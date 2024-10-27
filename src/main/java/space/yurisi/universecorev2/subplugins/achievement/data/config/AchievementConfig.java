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

    public List<Long> getBreak(){
        List<Long> breakCount = this.config.getLongList("break");
        return List.of(breakCount.getFirst(), breakCount.getLast());
    }

    public List<Long> getPlace(){
        List<Long> place = this.config.getLongList("place");
        return List.of(place.getFirst(), place.getLast());
    }

    public List<Long> getCoal(){
        List<Long> coal = this.config.getLongList("coal");
        return List.of(coal.getFirst(), coal.getLast());
    }

    public List<Long> getIron(){
        List<Long> iron = this.config.getLongList("iron");
        return List.of(iron.getFirst(), iron.getLast());
    }

    public List<Long> getGold(){
        List<Long> gold = this.config.getLongList("gold");
        return List.of(gold.getFirst(), gold.getLast());
    }

    public List<Long> getLapis(){
        List<Long> lap = this.config.getLongList("lapis");
        return List.of(lap.getFirst(), lap.getLast());
    }

    public List<Long> getRedStone(){
        List<Long> redStone = this.config.getLongList("redstone");
        return List.of(redStone.getFirst(), redStone.getLast());
    }

    public List<Long> getEmerald(){
        List<Long> emerald = this.config.getLongList("emerald");
        return List.of(emerald.getFirst(), emerald.getLast());
    }

    public List<Long> getDiamond(){
        List<Long> diamond = this.config.getLongList("diamond");
        return List.of(diamond.getFirst(), diamond.getLast());
    }

    public List<Long> getCopper(){
        List<Long> copper = this.config.getLongList("copper");
        return List.of(copper.getFirst(), copper.getLast());
    }

    public List<Long> getOre(){
        List<Long> ore = this.config.getLongList("ore");
        return List.of(ore.getFirst(), ore.getLast());
    }

    public List<Long> getFishing(){
        List<Long> fishing = this.config.getLongList("fishing");
        return List.of(fishing.getFirst(), fishing.getLast());
    }

    public List<Long> getGacha(){
        List<Long> gacha = this.config.getLongList("gacha");
        return List.of(gacha.getFirst(), gacha.getLast());
    }

    public List<Long> getKill(){
        List<Long> kill = this.config.getLongList("kill");
        return List.of(kill.getFirst(), kill.getLast());
    }

    public List<Long> getLogin(){
        List<Long> login = this.config.getLongList("login");
        return List.of(login.getFirst(), login.getLast());
    }

    public List<Long> getConsecutiveLogin(){
        List<Long> consecutiveLogin = this.config.getLongList("consecutive_login");
        return List.of(consecutiveLogin.getFirst(), consecutiveLogin.getLast());
    }
}

