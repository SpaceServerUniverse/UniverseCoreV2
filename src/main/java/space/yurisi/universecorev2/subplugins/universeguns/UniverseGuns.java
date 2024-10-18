package space.yurisi.universecorev2.subplugins.universeguns;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universeguns.command.GunCommand;
import space.yurisi.universecorev2.subplugins.universeguns.event.GunEvent;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;

public class UniverseGuns implements SubPlugin {

    private UniverseCoreAPIConnector connector;

    public void onEnable(UniverseCoreV2 core) {
        // Plugin startup logic
        DatabaseManager manager = UniverseCoreV2API.getInstance().getDatabaseManager();
        this.connector = new UniverseCoreAPIConnector(manager);
        core.getServer().getPluginManager().registerEvents(new GunEvent(core, connector), core);
        core.getCommand("ammo").setExecutor(new GunCommand(connector));
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "UniverseGuns";
    }

    @Override
    public String getVersion() {
        return "1.0.1";
    }
}
