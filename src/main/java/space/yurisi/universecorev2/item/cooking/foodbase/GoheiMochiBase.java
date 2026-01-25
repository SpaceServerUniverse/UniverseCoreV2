package space.yurisi.universecorev2.item.cooking.foodbase;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.RecipeBuilder;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryItemStack;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryMaterial;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryString;
import space.yurisi.universecorev2.item.cooking.ingredients.*;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;

import java.util.HashMap;

public final class GoheiMochiBase extends FoodBaseItem implements Craftable {

    public static final String id = "gohei_mochi_base";

    private final HashMap<Integer, RecipeEntryItemStack> recipe;

    public GoheiMochiBase(){
        super(
                GoheiMochiBase.id,
                "餅の素"
        );

        RecipeBuilder builder = RecipeBuilder.create()
                .setRecipeFromIndex(0, RecipeEntryString.of(Salt.id))
                .setRecipeFromIndex(1, RecipeEntryString.of(SoySauce.id))
                .setRecipeFromIndex(2, RecipeEntryMaterial.of(Material.SUGAR))
                .setRecipeFromIndex(3, RecipeEntryString.of(Rice.id));
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
        return RecipeId.GOHEI_MOCHI_BASE.getFlagId();
    }
}
