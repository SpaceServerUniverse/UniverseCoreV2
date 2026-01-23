package space.yurisi.universecorev2.item.cooking.constant;

import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.food.*;

import java.util.List;

public enum FoodId {

    NAPOLITAN(Napolitan.id),
    GOHEI_MOCHI(GoheiMochi.id);

    private final String stringId;

    FoodId(String stringId) {
        this.stringId = stringId;
    }

    public String getStringId() {
        return stringId;
    }

    public static List<CookingItem> getAllFoodItems() {
        FoodId[] values = FoodId.values();
        List<CookingItem> cookingItems = new java.util.ArrayList<>();
        for (FoodId recipeId: values){
            cookingItems.add((CookingItem) UniverseItem.getItem(recipeId.getStringId()));
        }
        return cookingItems;
    }
}
