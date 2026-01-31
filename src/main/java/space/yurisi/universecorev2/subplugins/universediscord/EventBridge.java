package space.yurisi.universecorev2.subplugins.universediscord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universediscord.config.DiscordConfiguration;
import space.yurisi.universecorev2.subplugins.universediscord.event.DiscordMessageListener;
import space.yurisi.universecorev2.subplugins.universediscord.event.MinecraftMessageListener;

import java.util.Optional;
import java.util.logging.Logger;

public class EventBridge {

    private static final Logger logger = Bukkit.getLogger();

    private final UniverseCoreV2 core;
    private final JDA jda;
    private final DiscordConfiguration config;

    private Optional<TextChannel> textChannel = Optional.empty();
    private Optional<DiscordMessageListener> discordListener = Optional.empty();
    private Optional<MinecraftMessageListener> minecraftListener = Optional.empty();

    public EventBridge(UniverseCoreV2 core, JDA jda, DiscordConfiguration config) {
        this.core = core;
        this.jda = jda;
        this.config = config;
    }

    public void registerEvents() {
        textChannel = getTextChannel();
        if (textChannel.isEmpty()) {
            logger.severe("[UniverseDiscord] テキストチャンネルの取得に失敗しました。IDを確認してください: " + config.getChannelId());
            return;
        }

        DiscordMessageListener discordListener = new DiscordMessageListener(config.getChannelId());
        jda.addEventListener(discordListener);
        this.discordListener = Optional.of(discordListener);

        MinecraftMessageListener minecraftListener = new MinecraftMessageListener(textChannel.get());
        core.getServer().getPluginManager().registerEvents(minecraftListener, core);
        this.minecraftListener = Optional.of(minecraftListener);

        logger.info("[UniverseDiscord] イベントリスナーが正常に登録されました。");
    }

    private @NotNull Optional<TextChannel> getTextChannel() {
        return Optional.ofNullable(jda.getGuildById(config.getGuildId()))
                .flatMap(guild -> Optional.ofNullable(guild.getTextChannelById(config.getChannelId())));
    }

}
