package space.yurisi.universecorev2.subplugins.playerinfoscoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.playerinfoscoreboard.task.ScoreBoardTask;

import java.util.HashMap;

public final class TaskManager {

    private final UniverseCoreV2 core;

    private final HashMap<String, Integer> list = new HashMap<>();

    public TaskManager(UniverseCoreV2 core) {
        this.core = core;
    }

    public void createScoreBoardTask(Player player) {
        ScoreBoardTask task = new ScoreBoardTask(player);
        task.runTaskTimer(core, 0L, 10L);
        this.list.put(player.getName(), task.getTaskId());
    }

    public void destroyScoreBoardTask(Player player) {
        Integer task_id = this.list.get(player.getName());
        if (task_id != null) {
            Bukkit.getScheduler().cancelTask(task_id);
        }

    }
}
