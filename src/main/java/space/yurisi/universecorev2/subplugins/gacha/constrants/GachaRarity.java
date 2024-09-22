package space.yurisi.universecorev2.subplugins.gacha.constrants;

public enum GachaRarity {

    UltraRare(10),
    SuperRare(11),
    Rare(99),
    Normal(100);

    private int id; // フィールドの定義

    GachaRarity(int id) { // コンストラクタの定義
        this.id = id;
    }
}
