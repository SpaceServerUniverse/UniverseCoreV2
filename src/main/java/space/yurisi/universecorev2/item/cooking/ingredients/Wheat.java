package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Wheat extends IngredientItem {

    public static final String id = "wheat";

    public Wheat() {
        super(
                Wheat.id,
                "小麦",
                ItemStack.of(INEDIBLE),
                null
        );
    }
}
