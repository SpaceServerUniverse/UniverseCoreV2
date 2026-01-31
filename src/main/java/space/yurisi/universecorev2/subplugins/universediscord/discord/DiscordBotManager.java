package space.yurisi.universecorev2.subplugins.universediscord.discord;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.universediscord.config.DiscordConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class DiscordBotManager {

    private static final Logger logger = Bukkit.getLogger();
    private static final int INITIALIZATION_TIMEOUT_SECONDS = 30;

    private final DiscordConfiguration config;
    private final Plugin plugin;
    private final AtomicReference<Optional<JDA>> jda = new AtomicReference<>(Optional.empty());
    private Optional<ScheduledTask> checkTask = Optional.empty();

    public DiscordBotManager(@NotNull DiscordConfiguration config, @NotNull Plugin plugin) {
        this.config = config;
        this.plugin = plugin;
    }

    /**
     * Discord Bot を初期化する．
     * JDA の接続自体にタイムアウトがなく，200 が返ってくるまで待つとんでも実装なので，Paper のメインスレッドを妨害する可能性がある．
     * このメソッドは Paper のメインスレッド以外で実行されることを想定している．
     *
     * @return 初期化結果を含む CompletableFuture
     */
    public @NotNull CompletableFuture<Optional<JDA>> initialize() {
        logger.info("[UniverseDiscord] Discord Bot を初期化しています...");
        CompletableFuture<Optional<JDA>> future = new CompletableFuture<>();

        // JDA の接続自体にタイムアウトがなく，200 が返ってくるまで待つとんでも実装なので，Paper のメインスレッドを妨害する可能性がある
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                List<GatewayIntent> intents = List.of(
                        GatewayIntent.GUILD_MESSAGES, // ギルドメッセージの受信に必要
                        GatewayIntent.MESSAGE_CONTENT // メッセージ関連の情報の取得に必要
                );

                JDA client = JDABuilder.createLight(config.getDiscordAPIToken(), intents)
                        .setAutoReconnect(true) // 自動再接続を有効化する． JDA で接続する Discord Bot が切断された場合に自動的に再接続を試みる．
                        .build();

                // ブロッキングだが，非同期スレッド上で実行されている
                JDA readyClient = client.awaitReady();

                Optional<TextChannel> channelOpt = validateGuildAndChannel(readyClient);
                if (channelOpt.isEmpty()) {
                    shutdown();
                    future.complete(Optional.empty());
                    return;
                }

                jda.set(Optional.of(client));
                logger.info("[UniverseDiscord] Discord Bot の初期化に成功しました。");
                future.complete(Optional.of(readyClient));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.severe("[UniverseDiscord] Discord Bot の初期化が中断されました: " + e.getMessage());
                future.complete(Optional.empty());
            } catch (Exception e) {
                logger.severe("[UniverseDiscord] Discord Bot の初期化に失敗しました: " + e.getMessage());
                future.complete(Optional.empty());
            }
        });

        // 30秒以内に初期化が完了しなければタイムアウトして UniverseDiscord を強制的に無効化する
        future.orTimeout(INITIALIZATION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .exceptionally(throwable -> {
                    logger.severe("[UniverseDiscord] Discord Bot の初期化がタイムアウトしました。 UniverseDiscord を無効化します。");
                    jda.get().ifPresent(JDA::shutdown);
                    jda.set(Optional.empty());
                    return Optional.empty();
                });

        return future;
    }

    private @NotNull Optional<TextChannel> validateGuildAndChannel(@NotNull JDA client) {
        Guild guild = client.getGuildById(config.getGuildId());
        if (guild == null) {
            logger.severe("[UniverseDiscord] ギルド ID が無効です: " + config.getGuildId());
            return Optional.empty();
        }

        TextChannel channel = guild.getTextChannelById(config.getChannelId());
        if (channel == null) {
            logger.severe("[UniverseDiscord] チャンネル ID が無効です: " + config.getChannelId());
            return Optional.empty();
        }

        if (channel.getType() != ChannelType.TEXT) {
            logger.severe("[UniverseDiscord] 指定されたチャンネルはテキストチャンネルではありません: " + config.getChannelId());
            return Optional.empty();
        }

        if (!channel.canTalk()) {
            logger.severe("[UniverseDiscord] ボットがチャンネルでメッセージを送信する権限がありません: " + config.getChannelId());
            return Optional.empty();
        }

        logger.info("[UniverseDiscord] ギルドとチャンネルの検証に成功しました。 guildId: " + config.getGuildId() + ", channelId: " + config.getChannelId());
        return Optional.of(channel);
    }

    /**
     * 起動時に生成した JDA インスタンスを正常にシャットダウンする
     */
    public void shutdown() {
        checkTask.ifPresent(ScheduledTask::cancel);
        checkTask = Optional.empty();

        jda.get().ifPresent(jdaInstance -> {
            logger.info("[UniverseDiscord] Discord Bot をシャットダウンしています...");
            try {
                jdaInstance.shutdown();
                // シャットダウンに10秒かかることはないが，安全側に倒すための実装
                if (!jdaInstance.awaitShutdown(10, TimeUnit.SECONDS)) {
                    logger.warning("[UniverseDiscord] Discord Bot のシャットダウンがタイムアウトしました。");
                    jdaInstance.shutdownNow();
                }
                logger.info("[UniverseDiscord] Discord Bot のシャットダウンに成功しました。");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.severe("[UniverseDiscord] Discord Bot のシャットダウンが中断されました: " + e.getMessage());
                jdaInstance.shutdownNow();
            }
        });
        jda.set(Optional.empty());
    }

    public @NotNull Optional<JDA> getJDA() {
        return jda.get();
    }
}
