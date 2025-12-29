package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Shimeji extends IngredientItem {

    public static final String id = "shimeji";

    public Shimeji() {
        super(
                id,
                "しめじ",
                ItemStack.of(EDIBLE)
        );
    }
}
