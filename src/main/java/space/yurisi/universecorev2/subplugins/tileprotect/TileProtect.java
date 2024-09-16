package space.yurisi.universecorev2.subplugins.tileprotect;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.tileprotect.command.lockCommand;
import space.yurisi.universecorev2.subplugins.tileprotect.event.player.TouchEvent;
import space.yurisi.universecorev2.subplugins.tileprotect.manager.LockManager;

public class TileProtect implements SubPlugin {

    public void onEnable(UniverseCoreV2 core) {
        LockManager lockManager = new LockManager();
        core.getServer().getPluginManager().registerEvents(new TouchEvent(lockManager),core);
        core.getCommand("lock").setExecutor(new lockCommand(lockManager));
    }


    public void onDisable() {

    }

    public String getName() {
        return "TileProtect";
    }

    public String getVersion() {
        return "1.0";
    }


}
