package space.yurisi.universecorev2.subplugins.autocollect;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

/**
 * 自動回収機能のメッセージをフォーマットするクラス。
 * プレイヤーに送信するメッセージの形式を統一します。
 */
public class AutoCollectMessageFormatter {

    public static final String PREFIX = "[収集AI]";

    private static final NamedTextColor PREFIX_COLOR = NamedTextColor.GREEN;
    private static final NamedTextColor MESSAGE_COLOR = NamedTextColor.WHITE;
    private static final NamedTextColor ERROR_COLOR = NamedTextColor.RED;
    private static final NamedTextColor TOGGLE_COLOR = NamedTextColor.YELLOW;

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

    /**
     * 自動回収機能の切り替えメッセージをフォーマットします。
     *
     * @param player 対象プレイヤー
     * @param state 自動回収機能の状態
     * @return フォーマットされた Component
     */
    public Component formatToggleMessage(Player player, AutoCollectState state) {
        String status = state.isEnabled() ? "有効化" : "無効化";
        return Component.text(PREFIX, PREFIX_COLOR)
                .append(Component.text(" "))
                .append(Component.text(player.getName() + "さんの自動回収機能を", MESSAGE_COLOR))
                .append(Component.text(status, TOGGLE_COLOR))
                .append(Component.text("しました。", MESSAGE_COLOR));
    }



}
