package space.yurisi.universecorev2.event.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.api.LuckPermsWrapper;

public class TeleportEvent implements Listener {

    private final Plugin plugin;

    public TeleportEvent(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        boolean allowFlight = LuckPermsWrapper.isUserInAdminOrDevGroup(player) && player.getGameMode() != GameMode.SURVIVAL
                || event.getTo().getWorld().getName().equals("lobby");

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setAllowFlight(allowFlight);
            }
        }.runTaskLater(plugin, 10);
    }
}
