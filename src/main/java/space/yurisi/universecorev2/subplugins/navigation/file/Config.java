package space.yurisi.universecorev2.subplugins.navigation.file;

import org.bukkit.configuration.Configuration;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.utils.ConfigReader;

public final class Config {

    private final UniverseCoreV2 core;

    private Configuration config = null;

    public Config(UniverseCoreV2 core) {
        this.core = core;
        init();
    }

    private void init() {
        ConfigReader configReader = new ConfigReader(core,"subplugins/", "navigations.yml");
        configReader.saveDefaultConfig();
        if (config != null) {
            configReader.reloadConfig();
        }
        this.config = configReader.getConfig();
    }

    public String getNavigation(String key) {
        return config.getString("links." + key);
    }
}
