package space.yurisi.universecorev2.subplugins.chestshop.transaction;

public class TransactionException extends Exception {
    private String friendlyMessage;

    public TransactionException(String friendlyMessage, String message) {
        super(message);
        this.friendlyMessage = friendlyMessage;
    }

    public TransactionException(String friendlyMessage, String message, Throwable cause) {
        super(message, cause);
        this.friendlyMessage = friendlyMessage;
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }
}
