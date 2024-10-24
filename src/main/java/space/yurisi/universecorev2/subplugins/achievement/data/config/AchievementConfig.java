package space.yurisi.universecorev2.subplugins.achievement.data.config;

import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.utils.ConfigReader;

import java.util.List;

public class AchievementConfig {

    private final UniverseCoreV2 core;
    private static AchievementConfig instance;

    private FileConfiguration config = null;

    public AchievementConfig (UniverseCoreV2 core){
        this.core = core;
        init();
        instance = this;
    }

    public static AchievementConfig getInstance() {
        return instance;
    }

    private void init(){
        ConfigReader configReader = new ConfigReader(this.core, "subplugins/", "achievement.yml");
        configReader.saveDefaultConfig();
        if (this.config != null) {
            configReader.reloadConfig();
        }
        this.config = configReader.getConfig();
    }

    public List<Long> getFlower(){
        List<Long> flower = this.config.getLongList("flower");
        return List.of(flower.getFirst(), flower.getLast());
    }
}

