package space.yurisi.universecorev2.subplugins.itemhat;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.itemhat.command.HatCommand;

public final class ItemHat implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("hat").setExecutor(new HatCommand());
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "ItemHat";
    }

    @Override
    public String getVersion() {
        return "1.2.3";
    }
}
