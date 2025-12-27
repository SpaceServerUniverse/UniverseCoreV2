package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.InterruptTransactionException;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * 指定したアイテムスタックを指定数量分だけインベントリに追加するアクション
 */
public class AddItemAction implements AtomicRollbackableAction {
    private @NotNull final Inventory inventory;
    private @NotNull final ItemStack item;

    public record NoSpaceLeftContext(
            @NotNull Inventory inventory,
            @NotNull ItemStack want,
            @NotNull ItemStack leftover
    ){};
    private @NotNull Consumer<NoSpaceLeftContext> whenNoSpaceLeft = (ctx) -> {};

    public AddItemAction(@NotNull Inventory inventory, @NotNull ItemStack item) {
        this.inventory = inventory;
        this.item = item;
    }

    /**
     * 適用時にインベントリに空きがなかったときに実行されるハンドラーを指定する
     * @param handler 呼び出し対象
     * @return this
     */
    public @NotNull AddItemAction whenNoSpaceLeft(@NotNull Consumer<NoSpaceLeftContext> handler) {
        whenNoSpaceLeft = handler;
        return this;
    }

    @Override
    public @NonNull RollbackFunc execute() throws InterruptTransactionException {
        HashMap<Integer, ItemStack> leftover = inventory.addItem(item.clone());

        if (!leftover.isEmpty()) {
            // 原子性の保証のために最初の状態に戻す
            inventory.removeItem(item.clone().subtract(leftover.get(0).getAmount()));
            whenNoSpaceLeft.accept(new NoSpaceLeftContext(
                    inventory,
                    item,
                    leftover.get(0)
            ));
            throw new InterruptTransactionException("No space left for inventory");
        }

        return () -> inventory.removeItem(item.clone());
    }
}
