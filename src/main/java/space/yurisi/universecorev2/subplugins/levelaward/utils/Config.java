package space.yurisi.universecorev2.subplugins.levelaward.utils;

import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.utils.ConfigReader;

import java.io.IOException;

public class Config {

    private final UniverseCoreV2 core;

    private FileConfiguration config = null;

    private ConfigReader configReader = null;

    public Config(UniverseCoreV2 core) {
        this.core = core;
        init();
    }

    private void init() {
        ConfigReader configReader = new ConfigReader(core, "subplugins/", "levelaward.yml");
        configReader.saveDefaultConfig();
        if (config != null) {
            configReader.reloadConfig();
        }
        this.config = configReader.getConfig();
    }

    public void register(String UUID) throws IOException {
        this.setTicket(UUID, 0);
    }

    public int getTicket(String UUID){
        return config.isSet(UUID) ? config.getInt(UUID) : null;
    }

    public void setTicket(String UUID,int ticket) throws IOException {
        this.config.set(UUID, ticket);
        this.config.save(this.config.saveToString());
        this.configReader.saveConfig();
    }
}
