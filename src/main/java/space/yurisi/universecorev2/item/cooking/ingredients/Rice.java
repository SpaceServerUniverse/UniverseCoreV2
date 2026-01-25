package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Rice extends IngredientItem {

    public static final String id = "rice";

    public Rice() {
        super(
                Rice.id,
                "米",
                ItemStack.of(INEDIBLE),
                "sugar"
        );
    }
}
