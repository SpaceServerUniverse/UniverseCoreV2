package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Pasta extends IngredientItem {

    public static final String id = "pasta";

    public Pasta() {
        super(
                Pasta.id,
                "パスタ",
                ItemStack.of(INEDIBLE),
                null
        );
    }
}
