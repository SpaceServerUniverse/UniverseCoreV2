package space.yurisi.universecorev2.subplugins.universeland;

import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universeland.command.LandCommand;
import space.yurisi.universecorev2.subplugins.universeland.manager.EventManager;
import space.yurisi.universecorev2.subplugins.universeland.utils.Config;
import space.yurisi.universecorev2.utils.ConfigReader;

import java.util.Objects;

public final class UniverseLand implements SubPlugin {

    private static UniverseLand instance;
    private Config config;
    private DatabaseManager databaseManager;

    public static UniverseLand getInstance() {
        return instance;
    }

    public void onEnable(UniverseCoreV2 core) {
        instance = this;
        this.config = new Config(core);
        Objects.requireNonNull(core.getCommand("land")).setExecutor(new LandCommand());
        EventManager.init(core);

        databaseManager = UniverseCoreV2API.getInstance().getDatabaseManager();

        core.getLogger().info("UniverseLandを読み込みました");
    }


    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "UniverseLand";
    }

    @Override
    public String getVersion() {
        return "1.4.5";
    }

    public Config getPluginConfig() {
        return config;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
