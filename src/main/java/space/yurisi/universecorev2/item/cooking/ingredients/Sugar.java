package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Sugar extends IngredientItem {

    public static final String id = "sugar";

    public Sugar() {
        super(
                id,
                "砂糖",
                org.bukkit.inventory.ItemStack.of(Material.SUGAR)
        );
    }
}
