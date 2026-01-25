package space.yurisi.universecorev2.subplugins.flysystem;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.flysystem.command.FlyCommand;

public final class Fly implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("fly").setExecutor(new FlyCommand());
    }

    @Override
    public void onDisable() {
    }

    @Override
    public String getName() {
        return "fly";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}