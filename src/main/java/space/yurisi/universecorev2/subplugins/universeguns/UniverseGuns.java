package space.yurisi.universecorev2.subplugins.universeguns;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universeguns.manager.EventManager;

public class UniverseGuns implements SubPlugin {

    public void onEnable(UniverseCoreV2 core) {
        // Plugin startup logic
        DatabaseManager manager = UniverseCoreV2API.getInstance().getDatabaseManager();
        EventManager.init(core);
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "getGuns";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}