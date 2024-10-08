package space.yurisi.universecorev2.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WarpEvent implements Listener {

    @EventHandler
    public void onWarp(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if(event.getTo().getWorld().getName().equals("lobby")){
            player.setAllowFlight(true);
            return;
        }
        player.setAllowFlight(false);
    }
}
