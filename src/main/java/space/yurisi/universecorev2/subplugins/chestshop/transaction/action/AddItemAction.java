package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.TransactionException;

public class AddItemAction implements AtomicRollbackableAction {
    private @NotNull final Inventory inventory;
    private @NotNull final ItemStack item;

    public AddItemAction(@NotNull Inventory inventory, @NotNull ItemStack item) {
        this.inventory = inventory;
        this.item = item;
    }

    @Override
    public @NonNull RollbackFunc execute() throws TransactionException {
        ItemStack[] snapshot = inventory.getStorageContents();
        boolean successfullyAdded = inventory.addItem(item).isEmpty();

        if (!successfullyAdded) {
            // 原子性の保証のために最初の状態に戻す
            inventory.setStorageContents(snapshot);
            throw new TransactionException(
                    "インベントリーがいっぱいです",
                    "failed to add item to inventory"
            );
        }

        return () -> inventory.setStorageContents(snapshot);
    }
}
