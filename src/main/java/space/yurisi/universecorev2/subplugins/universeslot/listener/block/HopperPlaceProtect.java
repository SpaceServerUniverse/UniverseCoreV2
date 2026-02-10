package space.yurisi.universecorev2.subplugins.universeslot.listener.block;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.block.Shelf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;

public class HopperPlaceProtect implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        BlockType blockType = event.getBlock().getType().asBlockType();
        if (blockType != BlockType.HOPPER) {
            return;
        }

        // 周り8ブロックをチェックし棚がある場合はキャンセル
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {

                    if(event.getBlock().getRelative(dx, dy, dz).getState() instanceof Shelf) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHopperSearch(InventoryMoveItemEvent event) {
        // ホッパートロッコ対策
        if(event.getSource().getHolder() instanceof InventoryHolder holder) {
            Location location = holder.getInventory().getLocation();
            if(location == null) return;
            Block block = location.getBlock();
            if(block.getState() instanceof Shelf) {
                event.setCancelled(true);
            }
        }
    }
}
