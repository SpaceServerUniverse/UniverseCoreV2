package space.yurisi.universecorev2.item.cooking.constant;

import space.yurisi.universecorev2.exception.InvalidRecipeException;
import space.yurisi.universecorev2.exception.NotCookingItemException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.UniverseItem;

public final class RecipeBuilder {

    private final String[] recipeMatrix;

    private RecipeBuilder(){
        recipeMatrix = new String[9];
    };

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

    public CookingItem[] build() {
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
        return recipe;
    }

    private static void checkIndex(int index) {
        if (index < 0 || index >= 9) throw new IndexOutOfBoundsException(index);
    }
}
