package space.yurisi.universecorev2.item.cooking.foodbase;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.exception.NotCookingItemException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.Edible;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.ingredients.GreenPepper;
import space.yurisi.universecorev2.item.cooking.ingredients.Pasta;
import space.yurisi.universecorev2.item.cooking.ingredients.Salt;
import space.yurisi.universecorev2.item.cooking.ingredients.Tomato;

public final class NapolitanBase extends FoodBaseItem implements Craftable, Edible {

    public static final String id = "napolitan_base";

    public NapolitanBase(){
        super(
                NapolitanBase.id,
                "ナポリタンの素",
                ItemStack.of(Material.RABBIT_STEW)
        );
    }

    @Override
    public CookingItem[] getRecipe() {
        CustomItem[] recipe = new CustomItem[9];
        recipe[0] = UniverseItem.getItem(Tomato.id);
        recipe[1] = UniverseItem.getItem(GreenPepper.id);
        recipe[2] = UniverseItem.getItem(Pasta.id);
        recipe[3] = UniverseItem.getItem(Salt.id);
        return this.toCookingRecipe(this, recipe);
    }

    @Override
    public boolean isShaped() {
        return true;
    }

    @Override
    public void onEat(Player player) {
        player.sendMessage("うまい！");
    }

    @Override
    public int getNutrition() {
        return 2;
    }

    @Override
    public float getSaturation() {
        return 10f;
    }
}
