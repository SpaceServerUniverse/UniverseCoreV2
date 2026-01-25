package space.yurisi.universecorev2.item.cooking.foodbase;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.RecipeBuilder;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryItemStack;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryString;
import space.yurisi.universecorev2.item.cooking.ingredients.GreenPepper;
import space.yurisi.universecorev2.item.cooking.ingredients.Pasta;
import space.yurisi.universecorev2.item.cooking.ingredients.Salt;
import space.yurisi.universecorev2.item.cooking.ingredients.Tomato;

import java.util.HashMap;

public final class NapolitanBase extends FoodBaseItem implements Craftable {

    public static final String id = "napolitan_base";

    private final HashMap<Integer, RecipeEntryItemStack> recipe;

    public NapolitanBase(){
        super(
                NapolitanBase.id,
                "ナポリタンの素"
        );

        RecipeBuilder builder = RecipeBuilder.create()
                .setRecipeFromIndex(0, RecipeEntryString.of(Tomato.id))
                .setRecipeFromIndex(1, RecipeEntryString.of(GreenPepper.id))
                .setRecipeFromIndex(2, RecipeEntryString.of(Pasta.id))
                .setRecipeFromIndex(3, RecipeEntryString.of(Salt.id));
        this.recipe = builder.build(this);
    }

    @Override
    public @NotNull HashMap<Integer, RecipeEntryItemStack> getRecipe() {
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
