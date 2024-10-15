package space.yurisi.universecorev2.event.player;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

public class TouchEvent implements Listener {

    @EventHandler
    public void onTouch(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block targetBlock = event.getClickedBlock();
        if(targetBlock == null){
            return;
        }
        if(targetBlock.getType() != Material.ANVIL && !(targetBlock.getState() instanceof ShulkerBox)){
            return;
        }
        if(player.getWorld().getName().equals("lobby") && !player.isOp()){
            event.setCancelled(true);
        }
    }
}
