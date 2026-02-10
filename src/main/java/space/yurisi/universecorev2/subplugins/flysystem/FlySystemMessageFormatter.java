package space.yurisi.universecorev2.subplugins.flysystem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class FlySystemMessageFormatter {

    public static final String PREFIX = "[飛行システムAI]";

    private static final NamedTextColor PREFIX_COLOR = NamedTextColor.GREEN;
    private static final NamedTextColor MESSAGE_COLOR = NamedTextColor.WHITE;
    private static final NamedTextColor ERROR_COLOR = NamedTextColor.RED;

    /**
     * 通常メッセージをフォーマットします。
     *
     * @param message メッセージ本文
     * @return フォーマットされた Component
     */
    public Component format(String message) {
        return Component.text(PREFIX, PREFIX_COLOR)
                .append(Component.text(" "))
                .append(Component.text(message, MESSAGE_COLOR));
    }

    /**
     * エラーメッセージをフォーマットします。
     *
     * @param errorMessage エラーメッセージ本文
     * @return フォーマットされた Component
     */
    public Component formatError(String errorMessage) {
        return Component.text(PREFIX, PREFIX_COLOR)
                .append(Component.text(" "))
                .append(Component.text(errorMessage, ERROR_COLOR));
    }
}
