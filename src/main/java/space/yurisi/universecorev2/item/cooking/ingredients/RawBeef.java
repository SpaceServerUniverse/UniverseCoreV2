package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class RawBeef extends IngredientItem {

    public static final String id = "beef_raw";

    public RawBeef() {
        super(
                RawBeef.id,
                "生の牛肉",
                ItemStack.of(INEDIBLE)
        );
    }
}
