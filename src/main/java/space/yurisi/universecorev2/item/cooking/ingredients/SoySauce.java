package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.IngredientItem;
import space.yurisi.universecorev2.item.cooking.RecipeBuilder;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryItemStack;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryMaterial;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryString;

import java.util.HashMap;

public final class SoySauce extends IngredientItem implements Craftable {

    public static final String id = "soy_sauce";

    private final HashMap<Integer, RecipeEntryItemStack> recipe;

    public SoySauce() {
        super(
                SoySauce.id,
                "醤油",
                ItemStack.of(INEDIBLE),
                "soysauce"
        );
        RecipeBuilder builder = RecipeBuilder.create()
                .setRecipeFromIndex(0, RecipeEntryMaterial.of(Material.WHEAT))
                .setRecipeFromIndex(1, RecipeEntryString.of(Salt.id))
                .setRecipeFromIndex(2, RecipeEntryString.of(Soy.id));
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
        return RecipeId.SOY_SAUCE.getFlagId();
    }
}
