package space.yurisi.universecorev2.subplugins.levelsystem.manager;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.levelsystem.task.BossBarTask;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.PlayerLevelData;

import java.util.HashMap;
import java.util.UUID;

public class TaskManager {

    private UniverseCoreV2 core;

    private final HashMap<UUID, Integer> list = new HashMap<>();

    public TaskManager(UniverseCoreV2 core) {
        this.core = core;
    }

    public void createBossBarTask(Player player, PlayerLevelData playerLevelData, BossBar bossBar) {
        BossBarTask task = new BossBarTask(playerLevelData, bossBar);
        task.runTaskTimer(this.core, 0L, 10L);
        this.list.put(player.getUniqueId(), task.getTaskId());
    }

    public void destroyBossBarTask(Player player) {
        Integer task_id = this.list.get(player.getUniqueId());
        if (task_id != null) {
            Bukkit.getScheduler().cancelTask(task_id);
        }

    }
}
