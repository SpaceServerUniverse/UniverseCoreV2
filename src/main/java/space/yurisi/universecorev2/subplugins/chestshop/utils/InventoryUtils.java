package space.yurisi.universecorev2.subplugins.chestshop.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    public static boolean RemoveItemFormChest(Inventory inv, ItemStack itemStack, int amountToRemove, boolean simulate) {
        if (inv.containsAtLeast(itemStack, amountToRemove)) {
            if (!simulate) {
                // real remove
                inv.removeItem(itemStack);
            }
            return true;
        }
        return false;
    }
}
