package space.yurisi.universecorev2.subplugins.xtpsystem;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.xtpsystem.file.Config;
import space.yurisi.universecorev2.subplugins.xtpsystem.command.XtpCommand;

public class XtpSystem implements SubPlugin {


    @Override
    public void onEnable(UniverseCoreV2 core) {
        Config config = new Config(core);
        core.getCommand("xtp").setExecutor(new XtpCommand(config));
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
