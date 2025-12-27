package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import space.yurisi.universecorev2.subplugins.chestshop.transaction.InterruptTransactionException;

@FunctionalInterface
public interface RollbackFunc {
    void execute() throws InterruptTransactionException;
}
