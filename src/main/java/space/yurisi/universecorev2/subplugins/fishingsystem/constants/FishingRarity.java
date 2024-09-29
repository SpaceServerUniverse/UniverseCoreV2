package space.yurisi.universecorev2.subplugins.fishingsystem.constants;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public enum FishingRarity {

    PickaxeRare(10),
    CreamRare(20),
    UltraRare(40),
    SuperRare(60),
    Rare(80),
    Normal(100);

    private int id; // フィールドの定義

    FishingRarity(int id) { // コンストラクタの定義
        this.id = id;
    }

    public static String getFishFeedJapanese(FishingRarity rarity){
        switch (rarity){
            case PickaxeRare -> {
                return "ピッケルレア";
            }
            case CreamRare ->{
                return "修復レア";
            }
            case UltraRare -> {
                return "ウルトラレア";
            }
            case SuperRare -> {
                return "スーパーレア";
            }
            case Rare -> {
                return "レア";
            }
            default -> {
                return "ノーマル";
            }
        }
    }

}
