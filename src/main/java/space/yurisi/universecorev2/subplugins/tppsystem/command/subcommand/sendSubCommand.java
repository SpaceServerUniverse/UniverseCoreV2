package space.yurisi.universecorev2.subplugins.tppsystem.command.subcommand;

import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.receive_request_menu.ReceiveRequestInventoryMenu;

import java.util.List;
import java.util.UUID;

public class sendSubCommand implements TPPSubCommand {

    public boolean execute(UniverseCoreAPIConnector connector, RequestManager requestManager, CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length < 2) {
            Message.sendWarningMessage(player, "[テレポートAI]", "プレイヤー名を入力してください．");
            return false;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            Message.sendErrorMessage(player, "[テレポートAI]", "プレイヤーが見つかりませんでした．");
            return false;
        }

        if (targetPlayer.equals(player)) {
            Message.sendErrorMessage(player, "[テレポートAI]", "自分自身にテレポート申請を送信することはできません．");
            return false;
        }

        if (requestManager.hasRequest(player)) {
            Message.sendErrorMessage(player, "[テレポートAI]", "既にテレポート申請を送信しています．");
            return false;
        }

        String playerName = targetPlayer.getName();
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        Message.sendNormalMessage(player, "[テレポートAI]", "§6" + playerName + " §fにテレポート申請を送信しました．");

        if (!connector.isExistsAutoTPPSetting(targetPlayer)) {
            sendRequest(requestManager, player, targetPlayer);
            return true;
        }

        try {
            if (connector.isAutoAccept(targetPlayer)) {
                player.teleport(targetPlayer);
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                Message.sendSuccessMessage(targetPlayer, "[テレポートAI]", "§6" + player.getName() + " §aからのテレポート申請を自動承認しました。");
                Message.sendSuccessMessage(player, "[テレポートAI]", "§6" + targetPlayer.getName() + " §aにテレポートしました。");
            } else {
                sendRequest(requestManager ,player, targetPlayer);
            }
        } catch (UserNotFoundException e) {
            Message.sendWarningMessage(player, "[テレポートAI]", "相手のユーザーデータが見つかりませんでした．");
        }

        return true;
    }

    private void sendRequest(RequestManager requestManager, Player player, Player targetPlayer) {
        targetPlayer.playSound(targetPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2, 1);
        Message.sendNormalMessage(targetPlayer, "[テレポートAI]", "§6" + player.getName() + " §fからテレポート申請が届きました．");
        Message.sendNormalMessage(targetPlayer, "[テレポートAI]", "§6/tpp§fメニューかコマンドで承認可否を選択できます．");
        Message.sendNormalMessage(targetPlayer, "[テレポートAI]", "§a[承認する]", ClickEvent.runCommand("/tpp accept " + player.getName()), player.getName() + " からのテレポート申請を承認します");
        Message.sendNormalMessage(targetPlayer, "[テレポートAI]", "§c[拒否する]", ClickEvent.runCommand("/tpp deny " + player.getName()), player.getName() + " からのテレポート申請を拒否します");
        requestManager.setRequest(player, targetPlayer);
        requestManager.setSearchReceiver(player, targetPlayer);
    }
}
