package space.yurisi.universecorev2.subplugins.levelsystem.listener.player;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.PlayerLevelData;

public class JoinEvent implements Listener {

    private LevelSystem main;

    public JoinEvent(LevelSystem main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        main.getPlayerBossBarManager().register(player);
        BossBar bossBar = main.getPlayerBossBarManager().get(player);

        PlayerLevelData data = main.getPlayerLevelDataManager().get(player);
        main.getTaskManager().createBossBarTask(player, data, bossBar);
    }
}
