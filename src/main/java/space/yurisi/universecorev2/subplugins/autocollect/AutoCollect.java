package space.yurisi.universecorev2.subplugins.autocollect;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;

public class AutoCollect implements SubPlugin {

    private static AutoCollect instance;
    private AutoCollectManager manager;

    public static AutoCollect getInstance() {
        return instance;
    }

    public AutoCollectManager getManager() {
        return manager;
    }

    @Override
    public void onEnable(UniverseCoreV2 core) {
        instance = this;
        this.manager = new AutoCollectManager();
        AutoCollectMessageFormatter messageFormatter = new AutoCollectMessageFormatter();

        core.getCommand("autocollect").setExecutor(new AutoCollectCommand(manager, messageFormatter));
        core.getServer().getPluginManager().registerEvents(new AutoCollectEvent(manager, messageFormatter), core);
    }

    @Override
    public void onDisable() {
        manager.clear();
        manager = null;
        instance = null;
    }

    @Override
    public String getName() {
        return "AutoCollect";
    }

    @Override
    public String getVersion() {
        return "2.0.0";
    }
}
