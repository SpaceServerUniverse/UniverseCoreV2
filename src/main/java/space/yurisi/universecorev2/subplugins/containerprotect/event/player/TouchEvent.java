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
import space.yurisi.universecorev2.subplugins.containerprotect.event.api.ContainerProtectAPI;
import space.yurisi.universecorev2.subplugins.containerprotect.manager.LockManager;
import space.yurisi.universecorev2.utils.Message;

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

        ContainerProtectAPI api = ContainerProtectAPI.getInstance();

        if (lockManager.hasFlag(player, LockManager.LOCK)) {
            event.setCancelled(true);

            if (api.getContainerProtect(blockLocation) != null) {
                Message.sendErrorMessage(player, "[金庫AI]", "既にこのコンテナは保護されています");
            } else {
                Message.sendSuccessMessage(player, "[金庫AI]", "コンテナの保護に成功しました");
                api.addContainerProtect(player, blockLocation);
            }

            lockManager.removeFlag(player);
            return;
        } else if (lockManager.hasFlag(player, LockManager.UNLOCK)) {
            event.setCancelled(true);

            ContainerProtect containerProtect = api.getContainerProtect(blockLocation);

            if(containerProtect == null) {
                Message.sendErrorMessage(player, "[金庫AI]", "このコンテナは保護されていません");
                lockManager.removeFlag(player);
                return;
            }

            if (player.isOp() || api.canAccessContainer(player, blockLocation)) {
                Message.sendSuccessMessage(player, "[金庫AI]", "このコンテナの保護を解除しました");
                api.removeContainerProtect(blockLocation);
            } else {
                Message.sendErrorMessage(player, "[金庫AI]", "このコンテナへのアクセス権限がありません");
            }

            lockManager.removeFlag(player);
            return;
        }

        if (!api.canAccessContainer(player, blockLocation)) {
            if(!player.isOp()){
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
            }
            ContainerProtect containerProtect = api.getContainerProtect(blockLocation);
            player.sendActionBar(Component.text("このコンテナは " + Bukkit.getOfflinePlayer(UUID.fromString(containerProtect.getUuid())).getName() + " によって保護されています"));
        }
    }
}
