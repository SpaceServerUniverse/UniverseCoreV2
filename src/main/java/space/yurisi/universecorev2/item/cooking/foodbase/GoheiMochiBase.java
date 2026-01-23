package space.yurisi.universecorev2.item.cooking.foodbase;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.constant.RecipeBuilder;
import space.yurisi.universecorev2.item.cooking.ingredients.*;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;

public final class GoheiMochiBase extends FoodBaseItem implements Craftable {

    public static final String id = "gohei_mochi_base";

    private final CookingItem[] recipe;

    public GoheiMochiBase(){
        super(
                GoheiMochiBase.id,
                "餅の素"
        );

        RecipeBuilder builder = RecipeBuilder.create()
                .setRecipeFromIndex(0, Salt.id)
                .setRecipeFromIndex(1, SoySauce.id)
                .setRecipeFromIndex(2, Sugar.id)
                .setRecipeFromIndex(3, Rice.id);
        this.recipe = builder.build();
    }

    @Override
    public @NotNull CookingItem[] getRecipe() {
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
