package space.yurisi.universecorev2.subplugins.mywarp.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;

public class MessageHelper {

    public static final String title = "[テレポートAI] ";

    public static Component getSuccessMessage(String message){
        return Component.text(title + message).color(TextColor.color(Color.GREEN.asRGB()));
    }

    public static  Component getErrorMessage(String message){
        return Component.text(title + message).color(TextColor.color(Color.RED.asRGB()));
    }
}
