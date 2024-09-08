package space.yurisi.universecorev2.subplugins.suicide;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.suicide.command.suCommand;

public final class Suicide implements SubPlugin {


    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("su").setExecutor(new suCommand());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "Suicide";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
