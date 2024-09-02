package space.yurisi.universecorev2.subplugins.levelsystem;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.levelsystem.manager.*;
import space.yurisi.universecorev2.subplugins.levelsystem.task.SaveDataTask;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.DayOfWeek;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.connector.UniverseCoreAPIConnector;

public final class LevelSystem {

    private PlayerLevelDataManager playerLevelDataManager;

    private PlayerBossBarManager playerBossBarManager;

    private TaskManager taskManager;

    private UniverseCoreAPIConnector connector;

    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getLogger().info("LevelSystem Loaded");
        new DayOfWeek();
        this.connector = new UniverseCoreAPIConnector();
        playerLevelDataManager = new PlayerLevelDataManager(this);
        playerBossBarManager = new PlayerBossBarManager();
        taskManager = new TaskManager(core);
        new LevelSystemAPI(getPlayerLevelDataManager(), getConnector());
        new EventManager(core, this);
        new CommandManager(core);
        new SaveDataTask(getPlayerLevelDataManager()).runTaskTimer(core, 0L, 6000L);
    }

    public UniverseCoreAPIConnector getConnector() {
        return this.connector;
    }

    public PlayerLevelDataManager getPlayerLevelDataManager() {
        return playerLevelDataManager;
    }

    public PlayerBossBarManager getPlayerBossBarManager() {
        return playerBossBarManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void onDisable() {
        LevelSystemAPI.getInstance().saveAll();
    }
}
