package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.TransactionException;

/**
 * 復元可能な原子性のある操作を表します
 * 各操作は全く実行されないか実行されきるかのいずれかになります
 */
public interface AtomicRollbackableAction {
    /**
     * 操作を実行します
     * 失敗した場合には実行前の状態に戻されます
     *
     * @throws TransactionException 実行に失敗した場合
     * @return ロールバック用の関数
     */
    @NotNull RollbackFunc execute() throws TransactionException;
}
