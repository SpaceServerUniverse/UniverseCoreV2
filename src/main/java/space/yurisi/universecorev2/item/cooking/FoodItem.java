package space.yurisi.universecorev2.item.cooking;

import org.bukkit.inventory.ItemStack;

public abstract class FoodItem extends CookingItem implements Edible {

    private final int nutrition;
    private final float saturation;

    protected FoodItem(String id, String name, ItemStack baseItem, int nutrition, float saturation) {
        super(id, name, baseItem);
        this.nutrition = nutrition;
        this.saturation = saturation;
    }

    @Override
    public int getNutrition() {
        return this.nutrition;
    }

    @Override
    public float getSaturation() {
        return this.saturation;
    }
}
