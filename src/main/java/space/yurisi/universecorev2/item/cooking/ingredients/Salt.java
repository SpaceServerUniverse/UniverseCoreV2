package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.Edible;
import space.yurisi.universecorev2.item.cooking.IngredientItem;
import space.yurisi.universecorev2.item.cooking.RecipeBuilder;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryItemStack;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryMaterial;

import java.util.HashMap;

public final class Salt extends IngredientItem implements Craftable {

    public static final String id = "salt";

    private final HashMap<Integer, RecipeEntryItemStack> recipe;

    public Salt() {
        super(
                Salt.id,
                "塩",
                ItemStack.of(INEDIBLE),
                "sugar"
        );
        RecipeBuilder builder = RecipeBuilder.create()
                .setRecipeFromIndex(0, RecipeEntryMaterial.of(Material.WATER_BUCKET));
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
        return RecipeId.SALT.getFlagId();
    }
}
