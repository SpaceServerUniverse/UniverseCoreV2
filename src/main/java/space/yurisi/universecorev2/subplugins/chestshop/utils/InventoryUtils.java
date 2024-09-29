package space.yurisi.universecorev2.subplugins.chestshop.utils;

import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    public static boolean RemoveItemFormChest(Chest chest, ItemStack itemStack, int amountToRemove) {
        Inventory chestInventory = chest.getBlockInventory();
        if (chestInventory == null) {
            return false;
        }
        for (ItemStack chestItemSlot : chestInventory.getContents()) {
            if (chestItemSlot != null && chestItemSlot.getType() == itemStack.getType()) {
                if (chestItemSlot.getAmount() >= amountToRemove) {
                    chestItemSlot.setAmount(chestItemSlot.getAmount() - amountToRemove);
                    return true;
                }
            }
        }
        return false;
    }
}
