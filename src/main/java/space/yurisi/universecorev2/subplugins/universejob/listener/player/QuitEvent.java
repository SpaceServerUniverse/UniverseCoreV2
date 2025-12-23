package space.yurisi.universecorev2.subplugins.universejob.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.PlayerLevelData;
import space.yurisi.universecorev2.subplugins.universejob.UniverseJob;
import space.yurisi.universecorev2.subplugins.universejob.manager.PlayerJobManager;

public class QuitEvent implements Listener {

    private UniverseJob main;

    public QuitEvent(UniverseJob universeJob) {
        this.main = universeJob;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerJobManager manager = main.getPlayerJobManager();
        manager.unregisterPlayer(player);
    }
}
