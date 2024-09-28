package space.yurisi.universecorev2.subplugins.fishingsystem.constants;

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
}
