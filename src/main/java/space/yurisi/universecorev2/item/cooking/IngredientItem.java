package space.yurisi.universecorev2.item.cooking;

import org.bukkit.inventory.ItemStack;

public abstract class IngredientItem extends CookingItem {

    protected IngredientItem(String id, String name, ItemStack baseItem) {
        super(id, name, baseItem);
    }
}
