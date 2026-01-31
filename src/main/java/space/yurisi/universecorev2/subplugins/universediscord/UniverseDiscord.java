package space.yurisi.universecorev2.subplugins.universediscord;

import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universediscord.config.DiscordConfiguration;
import space.yurisi.universecorev2.subplugins.universediscord.config.DiscordConfigurationLoader;
import space.yurisi.universecorev2.subplugins.universediscord.discord.DiscordBotManager;

import java.util.Optional;
import java.util.logging.Logger;

public class UniverseDiscord implements SubPlugin {

    private static final Logger logger = Bukkit.getLogger();

    private Optional<DiscordBotManager> botManager = Optional.empty();
    private Optional<EventBridge> eventBridge = Optional.empty();

    @Override
    public void onEnable(UniverseCoreV2 core) {
        try {
            DiscordConfigurationLoader configLoader = new DiscordConfigurationLoader(core);
            Optional<DiscordConfiguration> configOpt = configLoader.load();

            if (configOpt.isEmpty()) {
                logger.severe("[UniverseDiscord] 設定の読み込みに失敗しました。プラグインを無効化します。");
                return;
            }

            DiscordConfiguration config = configOpt.get();
            DiscordBotManager manager = new DiscordBotManager(config, core);
            this.botManager = Optional.of(manager);

            manager.initialize().thenAccept(jdaOpt -> {
                if (jdaOpt.isEmpty()) {
                    logger.severe("[UniverseDiscord] Discord Bot の初期化に失敗しました。プラグインを無効化します。");
                    cleanup();
                    return;
                }

                JDA client = jdaOpt.get();
                Bukkit.getScheduler().runTask(core, () -> {
                    try {
                        EventBridge bridge = new EventBridge(core, client, config);
                        bridge.registerEvents();
                        eventBridge = Optional.of(bridge);
                        logger.info("[UniverseDiscord] イベントブリッジが正常に登録されました。");
                    } catch (Exception e) {
                        logger.severe("[UniverseDiscord] イベントブリッジの登録中にエラーが発生しました: " + e.getMessage());
                        cleanup();
                    }
                });
                logger.info("[UniverseDiscord] Discord Bot が正常に初期化されました。");
            }).exceptionally(throwable -> {
                logger.severe("[UniverseDiscord] Discord Bot の初期化中にエラーが発生しました: " + throwable.getMessage());
                cleanup();
                return null;
            });
        } catch (Exception e) {
            logger.severe("[UniverseDiscord] プラグインの有効化中に予期しないエラーが発生しました: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        cleanup();
        logger.info("[UniverseDiscord] プラグインが無効化されました。");
    }

    private void cleanup() {
        botManager.ifPresent(DiscordBotManager::shutdown);
        botManager = Optional.empty();
        eventBridge = Optional.empty();
    }

    @Override
    public String getName() {
        return "UniverseDiscord";
    }

    @Override
    public String getVersion() {
        return "2.0.0";
    }
}
