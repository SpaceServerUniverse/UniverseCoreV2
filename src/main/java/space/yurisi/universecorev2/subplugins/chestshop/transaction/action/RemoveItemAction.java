package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.InterruptTransactionException;

import java.util.function.Consumer;

/**
 * インベントリからのアイテムの削除を原子的に行うアクション
 * <p>インベントリから全数量が削除できない場合、処理を実行せず
 * {@link InterruptTransactionException}を投げて実行前の状態を維持する</p>
 */
public class RemoveItemAction implements AtomicAction {
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

    /**
     * アイテムの削除を実行する
     * <p>補償時にインベントリの配置が戻ることまでは保証されない</p>
     * @return 補償処理(アイテムの追加)
     * @throws InterruptTransactionException インベントリに十分な量の指定アイテムがなかった場合
     */
    @Override
    public @NonNull Compensation execute() throws InterruptTransactionException {
        if (!inventory.containsAtLeast(item, item.getAmount())) {
            whenInsufficientItemHandler.accept(new InsufficientItemContext(inventory, item));
            throw new InterruptTransactionException("Insufficient item in inventory");
        }
        inventory.removeItem(item.clone());

        return () -> inventory.addItem(item.clone());
    }
}
