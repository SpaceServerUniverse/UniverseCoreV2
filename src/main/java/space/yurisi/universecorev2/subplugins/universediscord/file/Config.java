package space.yurisi.universecorev2.subplugins.universediscord.file;

import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.utils.ConfigReader;

public class Config {

    private final UniverseCoreV2 core;

    private FileConfiguration config = null;

    public Config(UniverseCoreV2 core) {
        this.core = core;
        init();
    }

    private void init() {
        ConfigReader configReader= new ConfigReader(core,"subplugins/", "universe-discord.yml");
        configReader.saveDefaultConfig();
        if (config != null) {
            configReader.reloadConfig();
        }
        this.config = configReader.getConfig();
    }

    public String getDiscordBotToken(){
        return this.config.getString("token");
    }

    public String getDiscordGuildId(){
        return this.config.getString("guild-id");
    }

    public String getDiscordChannelId(){
        return this.config.getString("channel-id");
    }
}