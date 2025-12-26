package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class RawPork extends IngredientItem {

    public static final String id = "pork_raw";

    public RawPork() {
        super(
                RawPork.id,
                "生の豚肉",
                ItemStack.of(Material.PORKCHOP)
        );
    }
}
