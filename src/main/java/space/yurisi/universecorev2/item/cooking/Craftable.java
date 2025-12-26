package space.yurisi.universecorev2.item.cooking;

import space.yurisi.universecorev2.exception.NotCookingItemException;
import space.yurisi.universecorev2.item.CustomItem;

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
        CookingItem[] ret = new CookingItem[8];
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
}
