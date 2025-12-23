package space.yurisi.universecorev2.event.world;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PortalCreateEvent implements Listener {

    @EventHandler
    public void onPortal(org.bukkit.event.world.PortalCreateEvent event) {
        event.setCancelled(true);
    }

    // エンドポータル生成
    @EventHandler(ignoreCancelled = true)
    public void onPortalFrameFill(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block b = e.getClickedBlock();
        if (b == null) return;

        if (b.getType() == Material.END_PORTAL_FRAME) {
            ItemStack hand = e.getItem();
            if (hand != null && hand.getType() == Material.ENDER_EYE) {
                e.setCancelled(true);
            }
        }
    }

    // エンドラ討伐後のポータル生成制限
    @EventHandler(ignoreCancelled = true)
    public void onBlockForm(BlockFormEvent e) {
        if (e.getNewState().getType() == Material.END_PORTAL) {
            if (e.getBlock().getWorld().getEnvironment() == World.Environment.THE_END) {
                e.setCancelled(true);
            }
        }
    }
}
