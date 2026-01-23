package space.yurisi.universecorev2.item.cooking.foodbase;

import org.jspecify.annotations.Nullable;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.ingredients.*;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;

public final class GoheiMochiBase extends FoodBaseItem implements Craftable {

    public static final String id = "mochi_base";

    private final CookingItem[] recipe;

    public GoheiMochiBase(){
        super(
                GoheiMochiBase.id,
                "餅の素"
        );

        CustomItem[] items = new CustomItem[9];
        items[0] = UniverseItem.getItem(Salt.id);
        items[1] = UniverseItem.getItem(SoySauce.id);
        items[2] = UniverseItem.getItem(Sugar.id);
        items[3] = UniverseItem.getItem(Rice.id);
        this.recipe = this.toCookingRecipe(this, items);
    }

    @Override
    public @Nullable CookingItem[] getRecipe() {
        return this.recipe;
    }

    @Override
    public boolean isShaped() {
        return false;
    }

    @Override
    public int getFlagId() {
        return RecipeId.GOHEI_MOCHI_BASE.getFlagId();
    }
}
