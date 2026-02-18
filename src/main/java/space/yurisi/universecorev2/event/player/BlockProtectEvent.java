package space.yurisi.universecorev2.event.player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import space.yurisi.universecorev2.api.LuckPermsWrapper;
import space.yurisi.universecorev2.utils.Message;

public class BlockProtectEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // 右クリックしたブロックデータが格納されている。
        // オブジェクトだけでなく、空気に対しても反応するので注意
        Block targetBlock = event.getClickedBlock();
        if (targetBlock == null) {
            return;
        }

        if (targetBlock.getType() != Material.ENCHANTING_TABLE) return;
        if (LuckPermsWrapper.isUserInAdminOrDevGroup(player)) {
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);
    }

}
