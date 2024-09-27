package space.yurisi.universecorev2.subplugins.chestshop.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.utils.Message;

public class SuperMessageHelper {
    public static final String title = "[チェストショップ管理AI] ";

    public static void sendSuccessMessage(Player player, String message) {
        Message.sendNormalMessage(player, title, message);
    }

    public static void sendErrorMessage(Player player, String message) {
        Message.sendErrorMessage(player, title, message);
    }

    public static void sendSuccessMessage(CommandSender player, String message) {
        Message.sendSuccessMessage((Player) player, title, message);
    }

    public static void sendErrorMessage(CommandSender player, String message) {
        Message.sendErrorMessage((Player) player, title, message);
    }
}
