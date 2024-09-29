package space.yurisi.universecorev2.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class StringHelper {

    public static String getComponent2String(Component component){
        String text = PlainTextComponentSerializer.plainText().serialize(component);
        if (text.startsWith("[") && text.endsWith("]")) {
            text = text.substring(1, text.length() - 1);
        }
        return text;
    }
}
