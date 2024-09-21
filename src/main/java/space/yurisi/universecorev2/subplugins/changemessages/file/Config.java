package space.yurisi.universecorev2.subplugins.changemessages.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.changemessages.ChangeMessages;
import space.yurisi.universecorev2.utils.ConfigReader;

public final class Config {

    private final UniverseCoreV2 core;

    private Configuration config = null;

    public Config(UniverseCoreV2 core){
        this.core = core;
        init();
    }

    private void init(){
        ConfigReader configReader = new ConfigReader(core,"subplugins/", "changemessages.yml");
        configReader.saveDefaultConfig();
        if (config != null) {
            configReader.reloadConfig();
        }
        this.config = configReader.getConfig();
    }

    public Boolean existsCustomJoinMessage(Player player){
        String result = this.config.getString("names."+player.getName());
        return result != null;
    }

    public String getCustomJoinMessage(Player player){
        return this.config.getString("names."+player.getName());
    }

}
