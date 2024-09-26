package space.yurisi.universecorev2.subplugins.chestshop.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;

public class SuperMessageHelper {
    public static final String title = "[チェストショップ管理AI] ";

    public static Component getSuccessMessage(String message){
        return Component.text(title + message).color(TextColor.color(Color.GREEN.asRGB()));
    }

    public static  Component getErrorMessage(String message){
        return Component.text(title + message).color(TextColor.color(Color.RED.asRGB()));
    }
}
