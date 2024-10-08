package space.yurisi.universecorev2.event.block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.net.http.WebSocket;

public class PlaceEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("lobby") && !player.isOp()) {
            event.setCancelled(true);
        }
    }
}
