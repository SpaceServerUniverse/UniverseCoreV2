package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class GreenPepper extends IngredientItem {

    public static final String id = "green_pepper";

    public GreenPepper() {
        super(
                GreenPepper.id,
                "ピーマン",
                ItemStack.of(Material.POISONOUS_POTATO)
        );
    }
}
