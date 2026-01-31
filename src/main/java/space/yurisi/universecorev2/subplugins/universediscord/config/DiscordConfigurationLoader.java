package space.yurisi.universecorev2.subplugins.universediscord.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.utils.ConfigReader;

import java.util.Optional;
import java.util.logging.Logger;

public class DiscordConfigurationLoader {

    private static final Logger logger = Bukkit.getLogger();
    private static final String configPath = "subplugins/";
    private static final String fileName = "universe-discord.yml";

    private final UniverseCoreV2 core;

    public DiscordConfigurationLoader(UniverseCoreV2 core) {
        this.core = core;
    }

    public @NotNull Optional<DiscordConfiguration> load() {
        try {
            ConfigReader configReader = new ConfigReader(core, configPath, fileName);
            configReader.saveDefaultConfig();
            FileConfiguration config = configReader.getConfig();
            return validate(config);
        } catch (Exception e) {
            logger.severe("[UniverseDiscord] Failed to load configuration: " + e.getMessage());
            return Optional.empty();
        }
    }

    private @NotNull Optional<DiscordConfiguration> validate(@NotNull FileConfiguration config) {
        Optional<String> tokenOpt = getAndValidateString(config, "discord-api-token", "Discord API Token");
        Optional<String> guildIdOpt = getAndValidateString(config, "guild-id", "Guild ID");
        Optional<String> channelIdOpt = getAndValidateString(config, "channel-id", "Channel ID");

        if (tokenOpt.isEmpty() || guildIdOpt.isEmpty() || channelIdOpt.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new DiscordConfiguration(
                tokenOpt.get(),
                guildIdOpt.get(),
                channelIdOpt.get()
        ));
    }

    private @NotNull Optional<String> getAndValidateString(
            @NotNull FileConfiguration config,
            @NotNull String path,
            @NotNull String fieldName
    ) {
        String value = config.getString(path);
        if (value == null || value.isEmpty()) {
            logger.severe("[UniverseDiscord] 値 [" + fieldName + "] が設定されていません。");
            return Optional.empty();
        }
        return Optional.of(value);
    }

}
