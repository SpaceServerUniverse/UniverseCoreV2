package space.yurisi.universecorev2.item.cooking;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.CustomItem;

public abstract class FoodItem extends CustomItem {

    private final int nutrition;
    private final float saturation;

    protected FoodItem(String id, String name, ItemStack baseItem, int nutrition, float saturation) {
        super(id, name, baseItem);
        this.nutrition = nutrition;
        this.saturation = saturation;
    }

    public abstract void onEat(Player player);

    /**
     * 満腹度を返します
     * 例:ステーキの場合は8
     * @return int
     */
    public int getNutrition() {
        return nutrition;
    }

    /**
     * 隠し満腹度を返します
     * 例:ステーキの場合は12.8
     * @return float
     */
    public float getSaturation() {
        return saturation;
    }
}
