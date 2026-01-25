package space.yurisi.universecorev2.exception;

public class NotItemException extends RuntimeException {
    public NotItemException(int pos) {
        super("Position " + pos + " does not contain a valid item.");
    }
}
