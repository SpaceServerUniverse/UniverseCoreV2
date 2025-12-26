package space.yurisi.universecorev2.item.cooking.food;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.FoodItem;
import org.bukkit.entity.Player;

public final class Napolitan extends FoodItem {

    public static final String id = "napolitan";

    public Napolitan() {
        super(
                Napolitan.id,
                "ナポリタン",
                ItemStack.of(Material.BEETROOT_SOUP),
                10,
                15.0f
        );
    }

    @Override
    public void onEat(Player player) {

    }
}
