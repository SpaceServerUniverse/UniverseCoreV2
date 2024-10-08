package space.yurisi.universecorev2.subplugins.containerprotect;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.containerprotect.command.lockCommand;
import space.yurisi.universecorev2.subplugins.containerprotect.command.unlockCommand;
import space.yurisi.universecorev2.subplugins.containerprotect.event.api.ContainerProtectAPI;
import space.yurisi.universecorev2.subplugins.containerprotect.event.inventory.MoveItemEvent;
import space.yurisi.universecorev2.subplugins.containerprotect.event.block.BreakEvent;
import space.yurisi.universecorev2.subplugins.containerprotect.event.player.TouchEvent;
import space.yurisi.universecorev2.subplugins.containerprotect.manager.LockManager;

public class ContainerProtect implements SubPlugin {

    public void onEnable(UniverseCoreV2 core) {
        new ContainerProtectAPI();

        LockManager lockManager = new LockManager();
        core.getServer().getPluginManager().registerEvents(new TouchEvent(lockManager), core);
        core.getServer().getPluginManager().registerEvents(new BreakEvent(), core);
        core.getServer().getPluginManager().registerEvents(new MoveItemEvent(), core);
        core.getCommand("lock").setExecutor(new lockCommand(lockManager));
        core.getCommand("unlock").setExecutor(new unlockCommand(lockManager));
    }

    public void onDisable() {

    }

    public String getName() {
        return "ContainerProtect";
    }

    public String getVersion() {
        return "1.0";
    }


}
