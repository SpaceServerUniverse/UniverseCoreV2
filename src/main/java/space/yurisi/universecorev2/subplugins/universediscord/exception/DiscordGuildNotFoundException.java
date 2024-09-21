package space.yurisi.universecorev2.subplugins.universediscord.exception;

public class DiscordGuildNotFoundException extends RuntimeException {
    public DiscordGuildNotFoundException(String message) {
        super(message);
    }
}
