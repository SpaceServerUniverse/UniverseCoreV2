package space.yurisi.universecorev2.item.cooking.entry;

import org.bukkit.inventory.ItemStack;

public record RecipeEntryItemStack(ItemStack itemStack) implements RecipeEntry {

    public static RecipeEntryItemStack of(ItemStack itemStack) {
        return new RecipeEntryItemStack(itemStack);
    }

    public ItemStack getItemStack() {
        return this.itemStack.clone();
    }
}
