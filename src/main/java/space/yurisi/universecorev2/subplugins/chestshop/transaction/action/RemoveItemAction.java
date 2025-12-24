package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.TransactionException;

public class RemoveItemAction implements AtomicRollbackableAction {
    private final @NotNull Inventory inventory;
    private final @NotNull ItemStack item;

    public RemoveItemAction(@NotNull Inventory inventory, @NotNull ItemStack item) {
        this.inventory = inventory;
        this.item = item;
    }

    @Override
    public @NonNull RollbackFunc execute() throws TransactionException {
        if (!inventory.containsAtLeast(item, item.getAmount())) {
            throw new TransactionException(
                    "チェスト内の在庫が不足しています",
                    "insufficient item in inventory"
            );
        }
        ItemStack[] snapshot = inventory.getStorageContents();
        inventory.removeItem(item);

        return () -> inventory.setStorageContents(snapshot);
    }
}
