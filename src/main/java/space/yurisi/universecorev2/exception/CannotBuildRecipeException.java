package space.yurisi.universecorev2.exception;

public class CannotBuildRecipeException extends RuntimeException{
    public CannotBuildRecipeException(String id) {
        super("Cannot build recipe with ID '" + id + "'.");
    }
}
