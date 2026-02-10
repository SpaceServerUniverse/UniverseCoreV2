package space.yurisi.universecorev2.subplugins.flysystem;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;

public final class FlySystem implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("fly").setExecutor(new FlyCommand());
        core.getServer().getPluginManager().registerEvents(new FlyEventListener(), core);
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
