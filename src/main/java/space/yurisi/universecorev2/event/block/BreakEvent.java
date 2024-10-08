package space.yurisi.universecorev2.event.block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getName().equals("lobby") && !player.isOp()) {
            event.setCancelled(true);
        }
    }
}
