package space.yurisi.universecorev2.subplugins.xtpsystem;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.xtpsystem.command.xtpCommand;

public class XTPSystem implements SubPlugin {


    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("xtp").setExecutor(new xtpCommand());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "XTPSystem";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
