package space.yurisi.universecorev2.event.player;

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

        switch (targetBlock.getType()) {
            case ENCHANTING_TABLE -> {
                if (LuckPermsWrapper.isUserInAdminOrDevGroup(player)) {
                    event.setCancelled(false);
                    break;
                }

                event.setCancelled(true);
            }
            case ANVIL -> {
                if (!player.getWorld().getName().equals("lobby")) {
                    return;
                }

                if (LuckPermsWrapper.isUserInAdminOrDevGroup(player)) {
                    event.setCancelled(false);
                    break;
                }

                event.setCancelled(true);
            }
        }
    }

}
