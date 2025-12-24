package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.jetbrains.annotations.Nullable;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.TransactionException;

public interface RollbackFunc {
    void execute() throws TransactionException;
}
