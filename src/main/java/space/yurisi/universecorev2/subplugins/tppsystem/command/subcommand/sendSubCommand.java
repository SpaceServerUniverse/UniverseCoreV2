package space.yurisi.universecorev2.subplugins.tppsystem.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
            player.sendMessage("プレイヤー名を入力してください。");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            player.sendMessage("プレイヤーが見つかりませんでした。");
            return true;
        }

        if (targetPlayer.equals(player)) {
            player.sendMessage("自分自身にテレポート申請を送信することはできません。");
            return true;
        }

        if (requestManager.hasRequest(player)) {
            player.sendMessage("既にテレポート申請を送信しています。");
            return true;
        }

        String playerName = targetPlayer.getName();
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        player.sendMessage("§6" + playerName + " §2にテレポート申請を送信しました。");

        if (!connector.isExistsAutoTPPSetting(targetPlayer)) {
            sendRequest(requestManager, player, targetPlayer);
            return true;
        }

        try {
            if (connector.isAutoAccept(targetPlayer)) {
                player.teleport(targetPlayer);
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                targetPlayer.sendMessage("§6" + player.getName() + " §2からのテレポート申請を自動承認しました。");
                player.sendMessage("§6" + targetPlayer.getName() + " §2にテレポートしました。");
            } else {
                sendRequest(requestManager ,player, targetPlayer);
            }
        } catch (UserNotFoundException e) {
            player.sendMessage("相手のユーザーデータが見つかりませんでした。");
        }

        return true;
    }

    private void sendRequest(RequestManager requestManager, Player player, Player targetPlayer) {
        targetPlayer.playSound(targetPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2, 1);
        targetPlayer.sendMessage("§6" + player.getName() + " §2からテレポート申請が届きました。");
        targetPlayer.sendMessage("§6/tppメニューから承認可否を選択できます。");
        targetPlayer.sendMessage("§6コマンドからも承認可能です。");
        targetPlayer.sendMessage("§6/tpp accept " + player.getName() + " §2で承認");
        targetPlayer.sendMessage("§6/tpp deny " + player.getName() + " §2で拒否");
        requestManager.setRequest(player, targetPlayer);
        requestManager.setSearchReceiver(player, targetPlayer);
    }
}
