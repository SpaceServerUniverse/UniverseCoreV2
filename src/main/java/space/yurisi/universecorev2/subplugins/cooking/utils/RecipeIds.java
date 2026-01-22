package space.yurisi.universecorev2.subplugins.cooking.utils;

public enum RecipeIds {

    NAPOLITAN(1, "napolitan"),
    GOHEI_MOCHI(2, "gohei_mochi");

    private final int id;
    private final String name;

    RecipeIds(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
