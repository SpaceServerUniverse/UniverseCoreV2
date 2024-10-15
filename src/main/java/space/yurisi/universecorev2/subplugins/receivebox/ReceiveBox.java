package space.yurisi.universecorev2.subplugins.receivebox;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.receivebox.command.addreceiveCommand;
import space.yurisi.universecorev2.subplugins.receivebox.command.receiveCommand;
import space.yurisi.universecorev2.subplugins.repaircream.command.repairCommand;

public class ReceiveBox implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("receive").setExecutor(new receiveCommand());
        core.getCommand("addreceive").setExecutor(new addreceiveCommand());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "ReceiveBox";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}