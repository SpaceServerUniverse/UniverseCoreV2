package space.yurisi.universecorev2.subplugins.tileprotect.event.player;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.TileProtect;
import space.yurisi.universecorev2.database.repositories.TileProtectRepository;
import space.yurisi.universecorev2.subplugins.tileprotect.manager.LockManager;

import java.util.Objects;


public class TouchEvent implements Listener {
    private LockManager lockManager;

    public TouchEvent(LockManager lockManager) {
        this.lockManager = lockManager;
    }

    @EventHandler
    public void onTouch(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Player player = event.getPlayer();
        Location blockLocation = block.getLocation();

        BlockState state = block.getState();

        if (!(state instanceof InventoryHolder)) {
            return;
        }

        TileProtectRepository tileProtectRepo = UniverseCoreV2API.getInstance().getDatabaseManager().getTileProtectRepository();

        if (lockManager.isSetState(player)) {
            if (tileProtectRepo.existsTileProtectFromLocation(blockLocation, player)) {
                player.sendMessage("ここにはすでに存在しております");
            } else {
                player.sendMessage("このブロックをロックしました！");
                tileProtectRepo.createTileProtect(player, blockLocation);
            }
            event.setCancelled(true);
            lockManager.deleteState(player);
            return;
        }

        if (tileProtectRepo.existsTileProtectFromLocation(blockLocation, player)) {
            TileProtect tileProtect = tileProtectRepo.getTileProtectFromLocation(blockLocation);
            if (!Objects.equals(tileProtect.getUuid(), player.getUniqueId().toString())) {
                event.setCancelled(true);
            }
        }
    }
}
