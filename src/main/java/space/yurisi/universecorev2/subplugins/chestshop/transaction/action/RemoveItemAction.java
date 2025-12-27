package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.InterruptTransactionException;

import java.util.function.Consumer;

/**
 * 指定したアイテムスタックを指定数量分だけインベントリから削除するアクション
 */
public class RemoveItemAction implements AtomicRollbackableAction {
    private final @NotNull Inventory inventory;
    private final @NotNull ItemStack item;

    public record InsufficientItemContext(
            Inventory inventory,
            ItemStack item
    ){}

    private @NotNull Consumer<InsufficientItemContext> whenInsufficientItemHandler = (ctx) -> {};

    /**
     * アイテム削除アクションを構築する
     * @param inventory 取り出し対象のインベントリ
     * @param item 削除対象のアイテムスタック
     */
    public RemoveItemAction(@NotNull Inventory inventory, @NotNull ItemStack item) {
        this.inventory = inventory;
        this.item = item;
    }

    /**
     * 適用時に十分な量のアイテムが見つからなかった場合に実行されるハンドラーを指定する
     * @param handler 呼び出し対象
     * @return this
     */
    public @NotNull RemoveItemAction whenInsufficientItem(@NotNull Consumer<InsufficientItemContext> handler) {
        whenInsufficientItemHandler = handler;
        return this;
    }

    @Override
    public @NonNull RollbackFunc execute() throws InterruptTransactionException {
        if (!inventory.containsAtLeast(item, item.getAmount())) {
            whenInsufficientItemHandler.accept(new InsufficientItemContext(inventory, item));
            throw new InterruptTransactionException("Insufficient item in inventory");
        }
        inventory.removeItem(item.clone());

        return () -> inventory.addItem(item.clone());
    }
}
