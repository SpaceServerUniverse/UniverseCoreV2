package space.yurisi.universecorev2.item.cooking;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.exception.CannotBuildRecipeException;
import space.yurisi.universecorev2.exception.InvalidRecipeException;
import space.yurisi.universecorev2.exception.NotCookingItemException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;

public final class RecipeBuilder {

    private final String[] recipeMatrix;

    private RecipeBuilder(){
        recipeMatrix = new String[9];
    }

    public static RecipeBuilder create() {
        return new RecipeBuilder();
    }

    public RecipeBuilder setRecipeFromIndex(int index, String id) {
        checkIndex(index);
        this.recipeMatrix[index] = id;
        return this;
    }

    public RecipeBuilder removeRecipeFromIndex(int index) {
        checkIndex(index);
        this.recipeMatrix[index] = null;
        return this;
    }

    public CookingItem[] build(CookingItem resultItem) {
        if(!(resultItem instanceof Craftable craftable)){
            throw new IllegalArgumentException("Result item must implement Craftable interface.");
        }
        CookingItem[] recipe = new CookingItem[9];
        boolean isThrownError = false;
        InvalidRecipeException exception = new InvalidRecipeException();
        for(int i = 0; i < 9; i++) {
            CustomItem item = UniverseItem.getItem(recipeMatrix[i]);
            if(item == null){
                recipe[i] = null;
                continue;
            }
            if(item instanceof CookingItem cookingItem) {
                recipe[i] = cookingItem;
                continue;
            }
            isThrownError = true;
            exception.addSuppressed(new NotCookingItemException(i, recipeMatrix[i]));
        }
        if(isThrownError){
            throw exception;
        }

        NamespacedKey recipeKey = new NamespacedKey(UniverseCoreV2.getInstance(), "recipe_" + resultItem.getId());
        boolean isAdded;
        if(craftable.isShaped()){
            ShapedRecipe shapedRecipe = getShapedRecipe(resultItem, recipeKey, recipe);
            isAdded = UniverseCoreV2.getInstance().getServer().addRecipe(shapedRecipe);
        }else{
            ShapelessRecipe shapelessRecipe = getShapelessRecipe(resultItem, recipeKey, recipe);
            isAdded = UniverseCoreV2.getInstance().getServer().addRecipe(shapelessRecipe);
        }

        if(!isAdded){
            throw new CannotBuildRecipeException(resultItem.getId());
        }

        return recipe;
    }

    private static @NotNull ShapedRecipe getShapedRecipe(CookingItem resultItem, NamespacedKey recipeKey, CookingItem[] recipe) {
        ShapedRecipe shapedRecipe = new ShapedRecipe(recipeKey, resultItem.getItem().clone());

        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        StringBuilder builder3 = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            builder1.append(recipe[i] != null ? Character.forDigit(i, 10) : ' ');
        }
        for (int i = 3; i < 6; i++) {
            builder2.append(recipe[i] != null ? Character.forDigit(i, 10) : ' ');
        }
        for (int i = 6; i < 9; i++) {
            builder3.append(recipe[i] != null ? Character.forDigit(i, 10) : ' ');
        }
        shapedRecipe.shape(
                builder1.toString(),
                builder2.toString(),
                builder3.toString()
        );

        for (int i = 0; i < 9; i++) {
            if (recipe[i] == null) continue;

            ItemStack choice = recipe[i].getItem().clone();
            choice.setAmount(1);

            shapedRecipe.setIngredient(Character.forDigit(i, 10), new RecipeChoice.ExactChoice(choice));
        }
        return shapedRecipe;
    }

    private static @NonNull ShapelessRecipe getShapelessRecipe(CookingItem resultItem, NamespacedKey recipeKey, CookingItem[] recipe) {
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(recipeKey, resultItem.getItem().clone());

        for (CookingItem ingredient: recipe) {
            if (ingredient != null) {
                ItemStack item = ingredient.getItem().clone();
                item.setAmount(1);
                shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(item));
            }
        }
        return shapelessRecipe;
    }

    private static void checkIndex(int index) {
        if (index < 0 || index >= 9) throw new IndexOutOfBoundsException(index);
    }
}
