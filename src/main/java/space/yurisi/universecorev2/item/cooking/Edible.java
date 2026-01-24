package space.yurisi.universecorev2.item.cooking;

import org.bukkit.entity.Player;

public interface Edible {

    /**
     * Called when the player eats the item
     *
     * @param player Player
     */
    void onEat(Player player);

    /**
     * Returns the nutrition value
     * Example: For steak, returns 8
     *
     * @return int
     */
    int getNutrition() ;

    /**
     * Returns the saturation value
     * Example: For steak, returns 12.8f
     *
     * @return float
     */
    float getSaturation() ;
}
