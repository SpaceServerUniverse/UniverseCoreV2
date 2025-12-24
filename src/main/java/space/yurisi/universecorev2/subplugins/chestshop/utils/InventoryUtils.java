package space.yurisi.universecorev2.subplugins.chestshop.utils;

import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    public static boolean RemoveItemFromChest(Chest chest, ItemStack itemStack) {
        Inventory chestInventory = chest.getInventory();

        // early return if no sufficient items in inventory.
        if (!chestInventory.containsAtLeast(itemStack, itemStack.getAmount())) {
            return false;
        }

        chestInventory.removeItem(itemStack);

        return true;
    }
}
