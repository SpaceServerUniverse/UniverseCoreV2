package space.yurisi.universecorev2.subplugins.tickfreezer.file;

import org.bukkit.configuration.Configuration;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.utils.ConfigReader;

import java.util.List;

public final class Config {
    private final UniverseCoreV2 core;
    private Configuration config;

    public Config(final UniverseCoreV2 core) {
        this.core = core;
        init();
    }

    private void init() {
        ConfigReader configReader = new ConfigReader(core, "subplugins/", "tickfreezer.yml");
        configReader.saveDefaultConfig();
        if (config != null) {
            configReader.reloadConfig();
        }
        this.config = configReader.getConfig();

    }

    public List<String> getDenyWorlds() {
        return this.config.getStringList("settings.deny-worlds");
    }
}
