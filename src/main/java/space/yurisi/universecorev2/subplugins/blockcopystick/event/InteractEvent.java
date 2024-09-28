package space.yurisi.universecorev2.subplugins.blockcopystick.event;

import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.UniverseItem;

import java.util.Objects;

public class InteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(player.getGameMode() != GameMode.CREATIVE){
            return;
        }

        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        ItemStack handItem = player.getInventory().getItemInMainHand();
        ItemMeta meta = handItem.getItemMeta();

        if(meta == null){
            return;
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();

        if(container.has(itemKey, PersistentDataType.STRING) && Objects.equals(container.get(itemKey, PersistentDataType.STRING), "block_copy_stick")){
            if (event.getHand() != EquipmentSlot.HAND) return;
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

            Block block = event.getClickedBlock();
            if (block == null) return;

            ItemStack itemStack = new ItemStack(block.getType());

            player.getInventory().setItem(0, itemStack);
        }

    }
}
