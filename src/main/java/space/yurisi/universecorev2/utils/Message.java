package space.yurisi.universecorev2.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class Message {
    
    public static void sendNormalMessage(Player target, String prefix, String message) {
        target.sendMessage(Component.text("§b" + prefix + " §f" + message));
    }

    public static void sendSuccessMessage(Player target, String prefix, String message) {
        target.sendMessage(Component.text("§b" + prefix + " §a" + message));
    }

    public static void sendWarningMessage(Player target, String prefix, String message) {
        target.sendMessage(Component.text("§b" + prefix + " §6" + message));
    }

    public static void sendErrorMessage(Player target, String prefix, String message) {
        target.sendMessage(Component.text("§b" + prefix + " §c" + message));
    }
}
