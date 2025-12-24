package space.yurisi.universecorev2.subplugins.chestshop.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    public static boolean RemoveItemFromChest(Inventory inventory, ItemStack itemStack) {
        // early return if no sufficient items in inventory.
        if (!inventory.containsAtLeast(itemStack, itemStack.getAmount())) {
            return false;
        }

        inventory.removeItem(itemStack);

        return true;
    }
}
