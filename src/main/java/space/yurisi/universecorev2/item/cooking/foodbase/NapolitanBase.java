package space.yurisi.universecorev2.item.cooking.foodbase;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.constant.RecipeBuilder;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;
import space.yurisi.universecorev2.item.cooking.ingredients.GreenPepper;
import space.yurisi.universecorev2.item.cooking.ingredients.Pasta;
import space.yurisi.universecorev2.item.cooking.ingredients.Salt;
import space.yurisi.universecorev2.item.cooking.ingredients.Tomato;

public final class NapolitanBase extends FoodBaseItem implements Craftable {

    public static final String id = "napolitan_base";

    private final CookingItem[] recipe;

    public NapolitanBase(){
        super(
                NapolitanBase.id,
                "ナポリタンの素"
        );

        RecipeBuilder builder = RecipeBuilder.create()
                .setRecipeFromIndex(0, Tomato.id)
                .setRecipeFromIndex(1, GreenPepper.id)
                .setRecipeFromIndex(2, Pasta.id)
                .setRecipeFromIndex(3, Salt.id);
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
        return RecipeId.NAPOLITAN_BASE.getFlagId();
    }
}
