package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.IngredientItem;
import space.yurisi.universecorev2.item.cooking.RecipeBuilder;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;

public final class SoySauce extends IngredientItem implements Craftable {

    public static final String id = "soy_sauce";

    private final CookingItem[] recipe;

    public SoySauce() {
        super(
                SoySauce.id,
                "醤油",
                ItemStack.of(INEDIBLE),
                "soysauce"
        );
        RecipeBuilder builder = RecipeBuilder.create()
                .setRecipeFromIndex(0, Wheat.id)
                .setRecipeFromIndex(1, Salt.id)
                .setRecipeFromIndex(2, Soy.id);
        this.recipe = builder.build(this);
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
        return RecipeId.SOY_SAUCE.getFlagId();
    }
}
