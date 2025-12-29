package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class SoySauce extends IngredientItem {

    public static final String id = "soy_sauce";

    public SoySauce() {
        super(
                SoySauce.id,
                "醤油",
                ItemStack.of(INEDIBLE)
        );
    }
}
