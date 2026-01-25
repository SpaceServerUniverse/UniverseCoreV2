package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.Edible;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Carrot extends IngredientItem implements Edible {

    public static final String id = "carrot";

    public Carrot() {
        super(
                Carrot.id,
                "にんじん",
                ItemStack.of(EDIBLE),
                "u_carrot"
        );
    }

    @Override
    public void onEat(Player player) {

    }

    @Override
    public int getNutrition() {
        return 1;
    }

    @Override
    public float getSaturation() {
        return 0;
    }
}
