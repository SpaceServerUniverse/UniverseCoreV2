package space.yurisi.universecorev2.subplugins.fishingsystem.constants;

public enum FishingRarity {
    UltraRare(10),
    SuperRare(11),
    Rare(99),
    Normal(100);

    private int id; // フィールドの定義

    FishingRarity(int id) { // コンストラクタの定義
        this.id = id;
    }
}
