package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import space.yurisi.universecorev2.subplugins.chestshop.transaction.TransactionException;

public interface RollbackFunc {
    void execute() throws TransactionException;
}
