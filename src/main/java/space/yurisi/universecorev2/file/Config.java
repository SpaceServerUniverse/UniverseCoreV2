package space.yurisi.universecorev2.file;

import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecorev2.UniverseCoreV2;

public class Config {

    private final UniverseCoreV2 main;

    private FileConfiguration config = null;

    public Config(UniverseCoreV2 main){
        this.main = main;
        init();
    }

    private void init(){
        main.saveDefaultConfig();
        if (config != null) {
            main.reloadConfig();
        }
        this.config = main.getConfig();
    }

    public String getDBHost(){
        return this.config.getString("db.host");
    }

    public int getDBPort(){
        return this.config.getInt("db.port");
    }

    public String getDBUserName(){
        return this.config.getString("db.username");
    }

    public String getDBUserPassword(){
        return this.config.getString("db.password");
    }

}
