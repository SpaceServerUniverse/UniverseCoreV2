package space.yurisi.universecorev2.subplugins.freemarket.data;

import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.utils.ConfigReader;

public class Config {

    private final UniverseCoreV2 core;

    private FileConfiguration config = null;

    public Config (UniverseCoreV2 core){
        this.core = core;
        init();
    }

    public void init(){
        ConfigReader configReader = new ConfigReader(this.core, "subplugins/", "market.yml");
        configReader.saveDefaultConfig();
        if (this.config != null) {
            configReader.reloadConfig();
        }
        this.config = configReader.getConfig();
    }

    public Long getLimit(){
        return this.config.getLong("limit");
    }
}
