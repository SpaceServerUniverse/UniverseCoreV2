package space.yurisi.universecorev2.item.cooking.food;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.Edible;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.FoodItem;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.item.cooking.FurnaceResult;
import space.yurisi.universecorev2.item.cooking.foodbase.NapolitanBase;

public final class Napolitan extends FoodItem implements Edible, FurnaceResult {

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

    @Override
    public FoodBaseItem getFurnaceBaseItem() {
        return (FoodBaseItem) UniverseItem.getItem(NapolitanBase.id);
    }
}
