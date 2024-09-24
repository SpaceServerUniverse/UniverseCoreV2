package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;

import java.util.HashMap;
import java.util.Map;

public class FireEvent implements Listener {


    @EventHandler
    public void onFireryou(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (!itemInHand.hasItemMeta()) {
                return;
            }
            ItemMeta meta = itemInHand.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String handItemID = container.get(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING);
            if (handItemID == null) {
                return;
            }
            if (handItemID.equals("r4c")) {
                player.sendMessage("Fire!");
            }
        }
    }
}
