package space.yurisi.universecorev2.item.cooking.food_base;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;

public final class NapolitanBase extends FoodBaseItem {

    public static final String id = "napolitan_base";

    public NapolitanBase(){
        super(
                NapolitanBase.id,
                "ナポリタンの素",
                ItemStack.of(Material.RABBIT_STEW)
        );
    }
}
