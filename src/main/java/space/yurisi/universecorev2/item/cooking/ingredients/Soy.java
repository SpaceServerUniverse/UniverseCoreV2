package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.Edible;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Soy extends IngredientItem {

    public static final String id = "soy";

    public Soy() {
        super(
                Soy.id,
                "大豆",
                ItemStack.of(INEDIBLE),
                null
        );
    }
}
