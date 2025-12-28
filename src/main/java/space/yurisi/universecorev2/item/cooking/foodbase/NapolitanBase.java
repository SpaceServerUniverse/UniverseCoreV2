package space.yurisi.universecorev2.item.cooking.foodbase;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.exception.InvalidRecipeException;
import space.yurisi.universecorev2.exception.InvalidRecipeSizeException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.ingredients.GreenPepper;
import space.yurisi.universecorev2.item.cooking.ingredients.Pasta;
import space.yurisi.universecorev2.item.cooking.ingredients.Salt;
import space.yurisi.universecorev2.item.cooking.ingredients.Tomato;

public final class NapolitanBase extends FoodBaseItem implements Craftable {

    public static final String id = "napolitan_base";

    private @Nullable CookingItem[] recipe;

    public NapolitanBase(){
        super(
                NapolitanBase.id,
                "ナポリタンの素",
                ItemStack.of(Material.RABBIT_STEW)
        );

        CustomItem[] items = new CustomItem[9];
        items[0] = UniverseItem.getItem(Tomato.id);
        items[1] = UniverseItem.getItem(GreenPepper.id);
        items[2] = UniverseItem.getItem(Pasta.id);
        items[3] = UniverseItem.getItem(Salt.id);
        try {
            this.recipe = this.toCookingRecipe(this, items);
        }catch (InvalidRecipeException | InvalidRecipeSizeException e){
            UniverseCoreV2.getInstance().getLogger().severe(e.getMessage());
            this.recipe = null;
        }
    }

    @Override
    public CookingItem[] @Nullable getRecipe() {
        return this.recipe;
    }

    @Override
    public boolean isShaped() {
        return true;
    }
}
