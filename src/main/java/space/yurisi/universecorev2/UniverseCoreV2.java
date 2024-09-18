package space.yurisi.universecorev2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.universecorev2.command.CommandManager;
import space.yurisi.universecorev2.database.DatabaseConnector;
import space.yurisi.universecorev2.event.EventManager;
import space.yurisi.universecorev2.file.Config;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.logs.filter.PasswordFilter;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.SubPluginInitializer;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.playerinfoscoreboard.PlayerInfoScoreBoard;
import space.yurisi.universecorev2.world.WorldInitializer;

public final class UniverseCoreV2 extends JavaPlugin {

    private DatabaseConnector connector;

    private Config config;

    private SubPluginInitializer sub_plugin;

    private static UniverseCoreV2 instance;

    @Override
    public void onEnable() {
        instance = this;

        ((Logger) LogManager.getRootLogger()).addFilter(new PasswordFilter());
        this.config = new Config(this);
        this.connector = new DatabaseConnector(
                getPluginConfig().getDBHost(),
                getPluginConfig().getDBPort(),
                getPluginConfig().getDBUserName(),
                getPluginConfig().getDBUserPassword()
        );
        new UniverseCoreV2API(this.connector);
        new EventManager(this);
        new CommandManager(this);
        new WorldInitializer();
        new UniverseItem();
        this.sub_plugin = new SubPluginInitializer(this);
        this.sub_plugin.onEnable();

    }

    @Override
    public void onDisable() {
        this.sub_plugin.onDisable();
        connector.close();
    }

    public Config getPluginConfig() {
        return this.config;
    }

    public static UniverseCoreV2 getInstance(){
        return instance;
    }
}
