package space.yurisi.universecorev2.item.cooking;

import org.bukkit.entity.Player;

public interface Edible {

    /**
     * 食べたときの処理を記述します
     *
     * @param player プレイヤー
     */
    void onEat(Player player);

    /**
     * 満腹度を返します
     * 例:ステーキの場合は8
     *
     * @return int
     */
    int getNutrition() ;

    /**
     * 隠し満腹度を返します
     * 例:ステーキの場合は12.8
     *
     * @return float
     */
    float getSaturation() ;
}
