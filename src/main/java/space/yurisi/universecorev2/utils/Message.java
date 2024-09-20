package space.yurisi.universecorev2.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * UniverseCoreV2 内のサブプラグインで使用するメッセージを送信するためのユーティリティクラス.
 * `[prefix] message` の形式でメッセージを送信でき, プレフィックスの色は固定されている.
 *
 * @author m1sk9
 */
public class Message {

    /**
     * プレイヤーに通常形式のメッセージを送信します.
     * メッセージ内容は色が白色で表示される. `message` には色付きの文字列を指定することもできる.
     *
     * @param target プレイヤーの送信先
     * @param prefix メッセージのプレフィックス ([管理AI] や [テレポートAI] などが望ましい. カッコいい)
     * @param message メッセージの内容.
     */
    public static void sendNormalMessage(Player target, String prefix, String message) {
        target.sendMessage(Component.text("§b" + prefix + " §f" + message));
    }

    /**
     * プレイヤーに成功したことを示すメッセージを送信します.
     * メッセージ内容は色が緑色で表示される. `message` には色付きの文字列を指定することもできる.
     *
     * @param target プレイヤーの送信先
     * @param prefix メッセージのプレフィックス ([管理AI] や [テレポートAI] などが望ましい. カッコいい)
     * @param message メッセージの内容.
     */
    public static void sendSuccessMessage(Player target, String prefix, String message) {
        target.sendMessage(Component.text("§b" + prefix + " §a" + message));
    }

    /**
     * プレイヤーに警告を示すメッセージを送信します.
     * メッセージ内容は色が黄色で表示される. `message` には色付きの文字列を指定することもできる.
     *
     * @param target プレイヤーの送信先
     * @param prefix メッセージのプレフィックス ([管理AI] や [テレポートAI] などが望ましい. カッコいい)
     * @param message メッセージの内容.
     */
    public static void sendWarningMessage(Player target, String prefix, String message) {
        target.sendMessage(Component.text("§b" + prefix + " §6" + message));
    }

    /**
     * プレイヤーにエラーを示すメッセージを送信します.
     * メッセージ内容は色が赤色で表示される. `message` には色付きの文字列を指定することもできる.
     *
     * @param target プレイヤーの送信先
     * @param prefix メッセージのプレフィックス ([管理AI] や [テレポートAI] などが望ましい. カッコいい)
     * @param message メッセージの内容.
     */
    public static void sendErrorMessage(Player target, String prefix, String message) {
        target.sendMessage(Component.text("§b" + prefix + " §c" + message));
    }
}
