package space.yurisi.universecorev2.exception;

public class InvalidRecipeSizeException extends RuntimeException{
    public InvalidRecipeSizeException(int givenSize) {
        super("Invalid recipe size: " + givenSize + ". Recipe size must be 9.");
    }
}
