package space.yurisi.universecorev2.subplugins.cooking.utils;

import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.FoodItem;
import space.yurisi.universecorev2.item.cooking.food.GoheiMochi;
import space.yurisi.universecorev2.item.cooking.food.Napolitan;
import space.yurisi.universecorev2.item.cooking.foodbase.GoheiMochiBase;
import space.yurisi.universecorev2.item.cooking.foodbase.NapolitanBase;

public class CookingItems {

    public static FoodBaseItem[] getAllCookingItems() {
        return new FoodBaseItem[]{
                (NapolitanBase) UniverseItem.getItem(NapolitanBase.id),
                (GoheiMochiBase) UniverseItem.getItem(GoheiMochiBase.id)
        };
    }

    public static FoodItem[] getAllFoodItems() {
        return new FoodItem[]{
                (Napolitan) UniverseItem.getItem(Napolitan.id),
                (GoheiMochi) UniverseItem.getItem(GoheiMochi.id)
        };
    }
}
