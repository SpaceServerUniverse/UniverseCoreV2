package space.yurisi.universecorev2.subplugins.chestshop.transaction;

/**
 * トランザクションの中断を通知するための例外
 */
public class InterruptTransactionException extends Exception {
    private boolean critical = false;

    public InterruptTransactionException(String message) {
        super(message);
    }

    public InterruptTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public void markAsCritical() {
        critical = true;
    }

    public boolean isCritical() {
        return critical;
    }
}
