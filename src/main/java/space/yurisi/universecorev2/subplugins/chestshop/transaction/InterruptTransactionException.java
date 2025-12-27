package space.yurisi.universecorev2.subplugins.chestshop.transaction;

/**
 * トランザクションの中断を通知するための例外
 */
public class InterruptTransactionException extends Exception {
    public InterruptTransactionException(String message) {
        super(message);
    }

    public InterruptTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
