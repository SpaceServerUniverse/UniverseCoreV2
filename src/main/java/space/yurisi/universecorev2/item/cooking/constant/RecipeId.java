package space.yurisi.universecorev2.item.cooking.constant;

import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.foodbase.*;
import space.yurisi.universecorev2.item.cooking.ingredients.SoySauce;

import java.util.List;

public enum RecipeId {

    NAPOLITAN_BASE(1, NapolitanBase.id),
    GOHEI_MOCHI_BASE(2, GoheiMochiBase.id),
    SOY_SAUCE(3, SoySauce.id);

    private final int id;
    private final String name;

    RecipeId(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getFlagId() {
        return id;
    }

    public String getStringId() {
        return name;
    }

    public static List<CookingItem> getAllCraftItems() {
        RecipeId[] values = RecipeId.values();
        List<CookingItem> cookingItems = new java.util.ArrayList<>();
        for (RecipeId recipeId: values){
            cookingItems.add((CookingItem) UniverseItem.getItem(recipeId.getStringId()));
        }
        return cookingItems;
    }
}
