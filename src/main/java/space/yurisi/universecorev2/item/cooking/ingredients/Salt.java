package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.Edible;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Salt extends IngredientItem {

    public static final String id = "salt";

    public Salt() {
        super(
                Salt.id,
                "塩",
                ItemStack.of(INEDIBLE),
                "sugar"
        );
    }
}
