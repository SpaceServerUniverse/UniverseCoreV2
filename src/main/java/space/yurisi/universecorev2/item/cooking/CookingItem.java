package space.yurisi.universecorev2.item.cooking;

import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.CustomItem;

public abstract class CookingItem extends CustomItem {

    protected CookingItem(String id, String name, ItemStack baseItem) {
        super(id, name, baseItem);
    }
}
