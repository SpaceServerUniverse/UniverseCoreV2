package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.InterruptTransactionException;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * インベントリへのアイテムの追加を原子的に行うアクション
 * <p>インベントリに全数量が追加できなかった場合、追加した分を削除し
 * {@link InterruptTransactionException}を投げて実行前の状態を維持する</p>
 */
public class AddItemAction implements AtomicAction {
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

    /**
     * アイテムを追加する。
     * <p>実行失敗時と補償時にインベントリの配置が戻ることまでは保証されない</p>
     *
     * @return 補償処理(アイテムの削除)
     * @throws InterruptTransactionException インベントリにアイテムを追加する余裕がなかった場合
     */
    @Override
    public @NonNull Compensation execute() throws InterruptTransactionException {
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
