package space.yurisi.universecorev2.subplugins.containerprotect.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import space.yurisi.universecorev2.database.models.ContainerProtect;
import space.yurisi.universecorev2.subplugins.containerprotect.event.api.ContainerProtectAPI;
import space.yurisi.universecorev2.utils.Message;

import java.util.UUID;

public class BreakEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();

        ContainerProtectAPI api = ContainerProtectAPI.getInstance();
        ContainerProtect containerProtect = api.getContainerProtect(blockLocation);

        if (containerProtect == null) return;

        if(api.canAccessContainer(player, blockLocation)) {
            api.removeContainerProtect(blockLocation);

            Message.sendSuccessMessage(player, "[金庫AI]", "このコンテナの保護を解除しました");
        }else{
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
            player.sendActionBar(Component.text("このコンテナは " + Bukkit.getOfflinePlayer(UUID.fromString(containerProtect.getUuid())).getName() + " によって保護されています"));
        }
    }
}
