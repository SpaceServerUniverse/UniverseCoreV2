package space.yurisi.universecorev2.subplugins.cooking.utils;

import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.foodbase.NapolitanBase;

public class CookingItems {

    public static FoodBaseItem[] getAllCookingItems() {
        return new FoodBaseItem[]{
                (FoodBaseItem) UniverseItem.getItem(NapolitanBase.id)
        };
    }
}
