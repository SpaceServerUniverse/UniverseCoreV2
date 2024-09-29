package space.yurisi.universecorev2.subplugins.fishingsystem.constants;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum FishFeed {
    NOTHING(0),
    DIAMOND(1),
    GOLD_INGOT(2),
    IRON_INGOT(3),
    RABBIT_FOOT(4),
    BREAD(5),
    ROTTEN_FLESH(6);

    private int id; // フィールドの定義

    FishFeed(int id) { // コンストラクタの定義
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static String getFishFeedJapanese(Material material){
        switch (material){
            case Material.DIAMOND -> {
                return "ダイヤモンド";
            }
            case Material.GOLD_INGOT ->{
                return "金のインゴット";
            }
            case Material.IRON_INGOT -> {
                return "鉄のインゴット";
            }
            case Material.RABBIT_FOOT -> {
                return "ミミズ";
            }
            case Material.BREAD -> {
                return "パン";
            }
            case Material.ROTTEN_FLESH -> {
                return "怪しい薬";
            }
            default -> {
                return "なし";
            }
        }
    }

    public static FishFeed getPlayerFishFeed(Material material) {
        switch (material){
            case Material.DIAMOND -> {
                return FishFeed.DIAMOND;
            }
            case Material.GOLD_INGOT ->{
                return FishFeed.GOLD_INGOT;
            }
            case Material.IRON_INGOT -> {
                return FishFeed.IRON_INGOT;
            }
            case Material.RABBIT_FOOT -> {
                return FishFeed.RABBIT_FOOT;
            }
            case Material.BREAD -> {
                return FishFeed.BREAD;
            }
            case Material.ROTTEN_FLESH -> {
                return FishFeed.ROTTEN_FLESH;
            }
            default -> {
                return FishFeed.NOTHING;
            }
        }
    }
}
