package space.yurisi.universecorev2.subplugins.universediscord.exception;

public class GuildNotFoundException extends RuntimeException {
    public GuildNotFoundException(String message) {
        super(message);
    }
}
