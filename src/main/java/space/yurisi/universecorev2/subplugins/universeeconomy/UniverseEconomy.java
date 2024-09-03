package space.yurisi.universecorev2.subplugins.universeeconomy;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universeeconomy.command.CommandManager;
import space.yurisi.universecorev2.subplugins.universeeconomy.event.EventManager;
import space.yurisi.universecorev2.subplugins.universeeconomy.file.Config;

public final class UniverseEconomy implements SubPlugin {

    private DatabaseManager databaseManager;

    private Config config;
    public void onEnable(UniverseCoreV2 core) {
        this.config = new Config();
        this.databaseManager = UniverseCoreV2API.getInstance().getDatabaseManager();
        new UniverseEconomyAPI(this.databaseManager, getPluginConfig());
        new EventManager(core, this);
        new CommandManager(core);
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    public @NotNull Config getPluginConfig() {
        return this.config;
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "UniverseEconomy";
    }

    @Override
    public String getVersion() {
        return "1.0.2";
    }
}
