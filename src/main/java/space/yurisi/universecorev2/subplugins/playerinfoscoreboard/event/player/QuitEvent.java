package space.yurisi.universecorev2.subplugins.playerinfoscoreboard.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.yurisi.universecorev2.subplugins.playerinfoscoreboard.TaskManager;

public final class QuitEvent implements Listener {

    private TaskManager taskManager;

    public QuitEvent(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        taskManager.destroyScoreBoardTask(event.getPlayer());
    }
}
