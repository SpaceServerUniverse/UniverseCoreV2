package space.yurisi.universecorev2.subplugins.chestshop;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.chestshop.command.ItemNameCommand;
import space.yurisi.universecorev2.subplugins.chestshop.event.EventManager;

public final class ChestShop implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        new EventManager(core);
        core.getCommand("item").setExecutor(new ItemNameCommand());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "chestShop";
    }

    @Override
    public String getVersion() {
        return "1.1.0";
    }
}
