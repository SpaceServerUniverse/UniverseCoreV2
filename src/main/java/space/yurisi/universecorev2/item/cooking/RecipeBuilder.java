package space.yurisi.universecorev2.item.cooking;

import org.bukkit.Material;
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
import space.yurisi.universecorev2.exception.NotItemException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntry;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryItemStack;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryMaterial;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryString;

import java.util.HashMap;

public final class RecipeBuilder {

    private final HashMap<Integer, RecipeEntry> entries;

    private RecipeBuilder(){
        entries = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            entries.put(i, null);
        }
    }

    public static RecipeBuilder create() {
        return new RecipeBuilder();
    }

    public RecipeBuilder setRecipeFromIndex(int index, RecipeEntry entry) {
        checkIndex(index);
        if(this.entries.containsKey(index)){
            this.entries.replace(index, entry);
        }
        this.entries.put(index, entry);
        return this;
    }

    public RecipeBuilder removeRecipeFromIndex(int index) {
        checkIndex(index);
        if(this.entries.containsKey(index)){
            this.entries.replace(index, null);
        }
        this.entries.put(index, null);
        return this;
    }

    public HashMap<Integer, RecipeEntryItemStack> build(CookingItem resultItem) {
        if(!(resultItem instanceof Craftable craftable)){
            throw new IllegalArgumentException("Result item must implement Craftable interface.");
        }
        HashMap<Integer, RecipeEntryItemStack> recipe = new HashMap<>();
        boolean isThrownError = false;
        InvalidRecipeException exception = new InvalidRecipeException();
        for(int i = 0; i < 9; i++) {
            if(this.entries.get(i) == null){
                recipe.put(i, null);
                continue;
            }
            if(this.entries.get(i) instanceof RecipeEntryMaterial recipeEntryMaterial){
                Material material = recipeEntryMaterial.getMaterial();
                if(material == Material.AIR){
                    recipe.put(i, null);
                    continue;
                }
                ItemStack itemStack = ItemStack.of(material, 1);
                recipe.put(i, RecipeEntryItemStack.of(itemStack));
                continue;
            }else if(this.entries.get(i) instanceof RecipeEntryString recipeEntryString){
                String itemId = recipeEntryString.getId();
                CustomItem customItem = UniverseItem.getItem(itemId);
                if(!(customItem instanceof CookingItem cookingItem)){
                    isThrownError = true;
                    exception.addSuppressed(new NotItemException(i));
                    continue;
                }
                recipe.put(i, RecipeEntryItemStack.of(cookingItem.getItem()));
                continue;
            }
            isThrownError = true;
            exception.addSuppressed(new NotItemException(i));
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

    private static @NotNull ShapedRecipe getShapedRecipe(CookingItem resultItem, NamespacedKey recipeKey, HashMap<Integer, RecipeEntryItemStack> recipe) {
        ShapedRecipe shapedRecipe = new ShapedRecipe(recipeKey, resultItem.getItem().clone());

        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        StringBuilder builder3 = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            builder1.append(recipe.get(i) != null ? Character.forDigit(i, 10) : ' ');
        }
        for (int i = 3; i < 6; i++) {
            builder2.append(recipe.get(i) != null ? Character.forDigit(i, 10) : ' ');
        }
        for (int i = 6; i < 9; i++) {
            builder3.append(recipe.get(i) != null ? Character.forDigit(i, 10) : ' ');
        }
        shapedRecipe.shape(
                builder1.toString(),
                builder2.toString(),
                builder3.toString()
        );

        for (int i = 0; i < 9; i++) {
            if (recipe.get(i) == null) continue;

            ItemStack choice = recipe.get(i).getItemStack();
            choice.setAmount(1);

            shapedRecipe.setIngredient(Character.forDigit(i, 10), new RecipeChoice.ExactChoice(choice));
        }
        return shapedRecipe;
    }

    private static @NonNull ShapelessRecipe getShapelessRecipe(CookingItem resultItem, NamespacedKey recipeKey, HashMap<Integer, RecipeEntryItemStack> recipe) {
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(recipeKey, resultItem.getItem().clone());

        for (int i = 0; i < 9; i++) {
            if (recipe.get(i) == null) continue;

            ItemStack item = recipe.get(i).getItemStack().clone();
            item.setAmount(1);
            shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(item));
        }

        return shapelessRecipe;
    }

    private static void checkIndex(int index) {
        if (index < 0 || index >= 9) throw new IndexOutOfBoundsException(index);
    }
}
