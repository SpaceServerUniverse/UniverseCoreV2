package space.yurisi.universecorev2.exception;

public class InvalidRecipeException extends RuntimeException {
    public InvalidRecipeException(String itemId) {
        super("Cannot read recipe data, itemId: " + itemId);
    }
}
