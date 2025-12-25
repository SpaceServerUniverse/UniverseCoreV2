package space.yurisi.universecorev2.subplugins.cooking.event;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.FoodItem;

public class CookingEventListener implements Listener {

    @EventHandler
    public void onFoodEat(PlayerItemConsumeEvent e){
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        if(!UniverseItem.isUniverseItem(item)) return;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.FOOD);
        if(!container.has(key)) return;
        CustomItem customItem = UniverseItem.getItem(container.get(key, PersistentDataType.STRING));
        if(customItem instanceof FoodItem foodItem){
            foodItem.onEat(player);
        }
    }
}
