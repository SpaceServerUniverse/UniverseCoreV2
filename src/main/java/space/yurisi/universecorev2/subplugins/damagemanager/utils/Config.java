package space.yurisi.universecorev2.subplugins.damagemanager.utils;

import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.utils.ConfigReader;

import java.util.List;

public class Config {

    private final UniverseCoreV2 core;

    private FileConfiguration config = null;

    public Config(UniverseCoreV2 core) {
        this.core = core;
        init();
    }

    private void init() {
        ConfigReader configReader = new ConfigReader(core, "subplugins/", "damage-manager.yml");
        configReader.saveDefaultConfig();
        if (config != null) {
            configReader.reloadConfig();
        }
        this.config = configReader.getConfig();
    }

    public List<String> getDenyWorlds() {
        return this.config.getStringList("deny-worlds");
    }
}