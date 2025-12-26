package space.yurisi.universecorev2.item.cooking;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.exception.NotCookingItemException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;

public interface Craftable {

    /**
     * レシピを返します
     *
     * @return CookingItem[]
     */
    CookingItem[] getRecipe();

    /**
     * レシピが形状付きかどうかを返します
     *
     * @return boolean
     */
    boolean isShaped();

    default CookingItem[] normalizeRecipe(CookingItem item, CustomItem[] recipe) throws NotCookingItemException {
        CookingItem[] ret = new CookingItem[9];
        for(int j = 0; j <= 8; j++){
            if(recipe[j] == null) {
                ret[j] = null;
                continue;
            }
            if(recipe[j] instanceof CookingItem cookingItem){
                ret[j] = cookingItem;
            }else{
                throw new NotCookingItemException(item, j);
            }
        }
        return ret;
    }

    default boolean isCraftedWith(ItemStack[] recipeToCheck) {
        if(recipeToCheck.length != 9){
            return false;
        }
        CookingItem checkedRecipe[] = new CookingItem[9];
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
