package space.yurisi.universecorev2.item.cooking.entry;

import org.bukkit.Material;

public record RecipeEntryMaterial(Material material) implements RecipeEntry {

    public static RecipeEntryMaterial of(Material material) {
        return new RecipeEntryMaterial(material);
    }

    public Material getMaterial() {
        return this.material;
    }
}
