package space.yurisi.universecorev2.item.cooking.entry;

public record RecipeEntryString(String id) implements RecipeEntry {

    public static RecipeEntryString of(String id) {
        return new RecipeEntryString(id);
    }

    public String getId() {
        return this.id;
    }
}
