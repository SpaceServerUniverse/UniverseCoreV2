package space.yurisi.universecorev2.subplugins.levelsystem.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.PlayerLevelData;

public class QuitEvent implements Listener {

    private LevelSystem main;

    public QuitEvent(LevelSystem main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerLevelData levelData = main.getPlayerLevelDataManager().get(player);
        main.getConnector().save(levelData);
        main.getPlayerLevelDataManager().unregister(player);

        main.getTaskManager().destroyBossBarTask(player);
        main.getPlayerBossBarManager().unregister(player);

    }
}
