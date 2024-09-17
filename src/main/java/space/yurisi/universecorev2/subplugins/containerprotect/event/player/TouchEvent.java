package space.yurisi.universecorev2.subplugins.containerprotect.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import space.yurisi.universecorev2.database.models.ContainerProtect;
import space.yurisi.universecorev2.subplugins.containerprotect.manager.LockManager;
import space.yurisi.universecorev2.subplugins.containerprotect.manager.ContainerProtectManager;

import java.util.UUID;


public class TouchEvent implements Listener {

    private final LockManager lockManager;

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

        ContainerProtectManager containerProtectManager = ContainerProtectManager.getInstance();

        if (lockManager.isSetState(player)) {
            if (containerProtectManager.getContainerProtect(blockLocation) != null) {
                player.sendMessage("ここにはすでに存在しております");
            } else {
                player.sendMessage("このブロックをロックしました！");
                containerProtectManager.addContainerProtect(player, blockLocation);
            }
            event.setCancelled(true);
            lockManager.deleteState(player);
            return;
        }

        if (!containerProtectManager.canAccessContainer(player, blockLocation)) {
            event.setCancelled(true);
            ContainerProtect containerProtect = containerProtectManager.getContainerProtect(blockLocation);
            player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
            player.sendActionBar(Component.text("このチェストは " + Bukkit.getOfflinePlayer(UUID.fromString(containerProtect.getUuid())).getName() + " によって保護されています"));
        }
    }
}
