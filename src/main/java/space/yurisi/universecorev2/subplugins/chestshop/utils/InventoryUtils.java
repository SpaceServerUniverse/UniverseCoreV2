package space.yurisi.universecorev2.subplugins.chestshop.utils;

import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    public static boolean RemoveItemFormChest(Inventory inv, ItemStack itemStack, int amountToRemove, boolean simulate) {
        for (ItemStack chestItemSlot : inv.getContents()) {
            if (chestItemSlot != null && chestItemSlot.getType() == itemStack.getType()) {
                if (chestItemSlot.getAmount() >= amountToRemove) {
                    if(simulate) return true;
                    chestItemSlot.setAmount(chestItemSlot.getAmount() - amountToRemove);
                    return true;
                }
            }
        }
        return false;
    }
}
