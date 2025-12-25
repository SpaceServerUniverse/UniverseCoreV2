package space.yurisi.universecorev2.item.cooking;

import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.CustomItem;

public abstract class FoodBaseItem extends CustomItem {

    protected FoodBaseItem(String id, String name, ItemStack baseItem) {
        super(id, name, baseItem);
    }
}
