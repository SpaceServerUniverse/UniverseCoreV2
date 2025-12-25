package space.yurisi.universecorev2.item.cooking;

import org.bukkit.inventory.ItemStack;

public abstract class FoodBaseItem extends CookingItem {

    protected FoodBaseItem(String id, String name, ItemStack baseItem) {
        super(id, name, baseItem);
    }
}
