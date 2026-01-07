package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.InterruptTransactionException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.Transaction;

/**
 * 復元可能な原子性を持つ操作の契約を定義する
 * @see Transaction
 */
public interface AtomicAction {
    /**
     * アクションを実行し、その操作を打ち消すための補償処理を返す
     * 実行に失敗した場合にはアクションの実行前の状態にこの関数内で戻す
     *
     * @throws InterruptTransactionException 実行に失敗した場合
     * @return 実行した操作を打ち消すための補償関数
     */
    @NotNull Compensation execute() throws InterruptTransactionException;
}
