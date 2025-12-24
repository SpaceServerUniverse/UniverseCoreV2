package space.yurisi.universecorev2.subplugins.chestshop.transaction;

import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.AtomicRollbackableAction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.RollbackFunc;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * 原子性のある一連の操作を表します
 */
public class Transaction {
    private final Queue<AtomicRollbackableAction> actions = new ArrayDeque<>();

    public void add(AtomicRollbackableAction action) {
        actions.add(action);
    }

    public void commit() throws TransactionException {
        Stack<RollbackFunc> rollbackStack = new Stack<>();
        try {
            while(!actions.isEmpty()) {
                rollbackStack.push(actions.poll().execute());
            }
        } catch (TransactionException e) {
            while(!rollbackStack.empty()) {
                try {
                    rollbackStack.pop().execute();
                } catch (TransactionException rollbackException) {
                    e.addSuppressed(rollbackException);
                }
            }

            throw e;
        }
    }
}
