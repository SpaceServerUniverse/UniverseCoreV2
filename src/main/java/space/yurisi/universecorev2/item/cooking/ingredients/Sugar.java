package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Sugar extends IngredientItem {

    public static final String id = "sugar";

    public Sugar() {
        super(
                Sugar.id,
                "砂糖",
                ItemStack.of(INEDIBLE)
        );
    }
}
