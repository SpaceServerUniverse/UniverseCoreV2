package space.yurisi.universecorev2.item.cooking.foodbase;

import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.CookingItem;
import space.yurisi.universecorev2.item.cooking.Craftable;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.subplugins.cooking.utils.RecipeIds;
import space.yurisi.universecorev2.item.cooking.ingredients.GreenPepper;
import space.yurisi.universecorev2.item.cooking.ingredients.Pasta;
import space.yurisi.universecorev2.item.cooking.ingredients.Salt;
import space.yurisi.universecorev2.item.cooking.ingredients.Tomato;

public final class NapolitanBase extends FoodBaseItem implements Craftable {

    public static final String id = "napolitan_base";

    private CookingItem[] recipe;

    public NapolitanBase(){
        super(
                NapolitanBase.id,
                "ナポリタンの素"
        );

        CustomItem[] items = new CustomItem[9];
        items[0] = UniverseItem.getItem(Tomato.id);
        items[1] = UniverseItem.getItem(GreenPepper.id);
        items[2] = UniverseItem.getItem(Pasta.id);
        items[3] = UniverseItem.getItem(Salt.id);
        this.recipe = this.toCookingRecipe(this, items);
    }

    @Override
    public CookingItem[] getRecipe() {
        return this.recipe;
    }

    @Override
    public boolean isShaped() {
        return false;
    }

    @Override
    public int getFlagId() {
        return RecipeIds.NAPOLITAN.getId();
    }
}
