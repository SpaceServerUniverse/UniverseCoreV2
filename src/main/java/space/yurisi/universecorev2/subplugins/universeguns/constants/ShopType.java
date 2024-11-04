package space.yurisi.universecorev2.subplugins.universeguns.constants;

public enum ShopType {

    HGShop("hg"),
    SMGShop("smg"),
    SGShop("sg"),
    ARShop("ar"),
    SRShop("sr"),
    LMGShop("lmg"),
    EXShop("ex"),
    EQUIPMENTShop("armor");

    private String name;

    ShopType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
