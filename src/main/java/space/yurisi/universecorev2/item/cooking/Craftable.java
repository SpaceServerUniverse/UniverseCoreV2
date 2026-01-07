package space.yurisi.universecorev2.item.cooking;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.Nullable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.database.models.CookingRecipe;
import space.yurisi.universecorev2.database.repositories.CookingRecipeRepository;
import space.yurisi.universecorev2.exception.CookingRecipeNotFoundException;
import space.yurisi.universecorev2.exception.InvalidRecipeException;
import space.yurisi.universecorev2.exception.InvalidRecipeSizeException;
import space.yurisi.universecorev2.exception.NotCookingItemException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.subplugins.cooking.utils.RecipeFlagOps;

import java.util.UUID;

public interface Craftable {

    /**
     * レシピを返します
     *
     * @return CookingItem[]
     */
    @Nullable
    CookingItem[] getRecipe();

    /**
     * レシピが形状付きかどうかを返します
     *
     * @return boolean
     */
    boolean isShaped();

    /**
     * レシピのフラグIDを返します
     *
     * @return int
     */
    int getFlagId();

    default CookingItem[] toCookingRecipe(CookingItem item, CustomItem[] recipe) throws InvalidRecipeSizeException, InvalidRecipeException {
        if(recipe.length != 9){
            throw new InvalidRecipeSizeException(recipe.length);
        }
        CookingItem[] ret = new CookingItem[9];
        boolean isThrownError = false;
        InvalidRecipeException exception = new InvalidRecipeException(item.getId());
        for(int j = 0; j <= 8; j++){
            if(recipe[j] == null) {
                ret[j] = null;
                continue;
            }
            if(recipe[j] instanceof CookingItem cookingItem){
                ret[j] = cookingItem;
            }else{
                isThrownError = true;
                exception.addSuppressed(new NotCookingItemException(item, j));
            }
        }
        if(isThrownError){
            throw exception;
        }
        return ret;
    }

    default boolean canCraftedWith(ItemStack[] recipeToCheck, UUID uuid) {
        if(recipeToCheck.length != 9){
            return false;
        }
        try {
            CookingRecipe cookingRecipe = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(CookingRecipeRepository.class).getRecipeFlagsFromPlayer(uuid.toString());
            if(!RecipeFlagOps.contains(RecipeFlagOps.fromBytes(cookingRecipe.getRecipe()), this.getFlagId())) return false;
        } catch (CookingRecipeNotFoundException e) {
            return false;
        }
        CookingItem[] checkedRecipe = new CookingItem[9];
        for (int i = 0; i <= 8; i++) {
            ItemStack item = recipeToCheck[i];
            if (item == null) continue;
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.COOKING_ITEM);
            if(!container.has(key)) return false;
            CustomItem customItem = UniverseItem.getItem(container.get(key, PersistentDataType.STRING));
            if(!(customItem instanceof CookingItem cookingItem)) return false;
            checkedRecipe[i] = cookingItem;
        }
        CookingItem[] recipe = this.getRecipe();
        if(this.isShaped()){
            for(int j = 0; j <= 8; j++){
                CookingItem requiredItem = recipe[j];
                CookingItem checkedItem = checkedRecipe[j];
                if(requiredItem == null && checkedItem == null) continue;
                if(requiredItem == null || checkedItem == null) return false;
                if(!requiredItem.getId().equals(checkedItem.getId())) return false;
            }
        }else{
            for(int k = 0; k <= 8; k++){
                CookingItem requiredItem = recipe[k];
                if(requiredItem == null) continue;
                boolean found = false;
                for(int l = 0; l <= 8; l++){
                    CookingItem checkedItem = checkedRecipe[l];
                    if(checkedItem == null) continue;
                    if(!requiredItem.getId().equals(checkedItem.getId())) continue;
                    found = true;
                    checkedRecipe[l] = null;
                    break;
                }
                if(!found) return false;
            }
        }
        return true;
    }
}
