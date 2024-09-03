package space.yurisi.universecorev2.subplugins.changemessages.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.changemessages.ChangeMessages;

public final class Config {

    private final UniverseCoreV2 core;

    private Configuration config = null;

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

    public Boolean existsCustomJoinMessage(Player player){
        String result = this.config.getString("customname."+player.getName());
        return result != null;
    }

    public String getCustomJoinMessage(Player player){
        return this.config.getString("customname."+player.getName());
    }

}
