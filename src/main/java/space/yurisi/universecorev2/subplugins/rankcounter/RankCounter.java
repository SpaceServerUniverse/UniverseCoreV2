package space.yurisi.universecorev2.subplugins.rankcounter;

import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementDataManager;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.CounterModelManager;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.EventManager;
import space.yurisi.universecorev2.subplugins.SubPlugin;

public final class RankCounter implements SubPlugin {

    private CounterModelManager counterManager;

    public void onEnable(UniverseCoreV2 core) {
        this.counterManager = new CounterModelManager();
        new EventManager(core, getCounterManager());
        AchievementDataManager.setManager(getCounterManager());
    }

    public CounterModelManager getCounterManager() {
        return counterManager;
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "rankcounter";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
