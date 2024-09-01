package space.yurisi.universecorev2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.universecorev2.command.CommandManager;
import space.yurisi.universecorev2.database.DatabaseConnector;
import space.yurisi.universecorev2.event.EventManager;
import space.yurisi.universecorev2.file.Config;
import space.yurisi.universecorev2.logs.filter.PasswordFilter;

public final class UniverseCoreV2 extends JavaPlugin {

    private DatabaseConnector connector;

    private Config config;

    @Override
    public void onEnable() {
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
    }

    @Override
    public void onDisable() {
        connector.close();
    }

    public Config getPluginConfig() {
        return this.config;
    }
}
