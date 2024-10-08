package space.yurisi.universecorev2.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportEvent implements Listener {

    private final Plugin plugin;

    public TeleportEvent(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Boolean allowFlight = false;
        if(player.isOp()){
            allowFlight = true;
        }
        if(event.getTo().getWorld().getName().equals("lobby")){
            allowFlight = true;
        }
        Boolean finalAllowFlight = allowFlight;

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setAllowFlight(finalAllowFlight);
            }
        }.runTaskLater(plugin, 10);
    }
}
