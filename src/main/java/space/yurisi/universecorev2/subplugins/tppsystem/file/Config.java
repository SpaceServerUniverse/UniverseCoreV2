package space.yurisi.universecorev2.subplugins.tppsystem.file;


import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecorev2.UniverseCoreV2;

import java.util.List;

public class Config {

    private final UniverseCoreV2 core;

    private FileConfiguration config = null;

    public Config(UniverseCoreV2 core){
        this.core = core;
        init();
    }

    private void init(){
        core.saveDefaultConfig();
        if (config != null) {
            core.reloadConfig();
        }
        this.config = core.getConfig();
    }

    public List<String> getDenyWorlds(){
        return this.config.getStringList("settings.deny-worlds");
    }
}
