package space.yurisi.universecorev2.subplugins.universeland.utils;

import org.apache.logging.log4j.core.Core;
import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeland.UniverseLand;

import java.util.List;

public class Config {

    private final UniverseCoreV2 core;

    private FileConfiguration config = null;

    public Config(UniverseCoreV2 core) {
        this.core = core;
        init();
    }

    private void init() {
        core.saveDefaultConfig();
        if (config != null) {
            core.reloadConfig();
        }
        this.config = core.getConfig();
    }

    public Long getLandPrice() {
        return this.config.getLong("universe-land.land-price");
    }

    public List<String> getDenyWorlds() {
        return this.config.getStringList("universe-land.deny-worlds");
    }
}