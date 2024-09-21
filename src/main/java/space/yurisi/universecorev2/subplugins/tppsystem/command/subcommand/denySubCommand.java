package space.yurisi.universecorev2.subplugins.tppsystem.command.subcommand;

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

public class denySubCommand implements TPPSubCommand {

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

        String playerName = targetPlayer.getName();

        if (!requestManager.isRequestExists(targetPlayer, player)) {
            Message.sendErrorMessage(player, "[テレポートAI]", "§c" + playerName + "からのテレポート申請はありません．");
            return false;
        }

        targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        Message.sendNormalMessage(player, "[テレポートAI]", "§6" + playerName + " §fのテレポート申請を拒否しました.");
        Message.sendNormalMessage(targetPlayer, "[テレポートAI]", "テレポート申請が拒否されました.");

        requestManager.removeSearchReceiver(targetPlayer);
        requestManager.removeRequest(targetPlayer, player);

        requestManager.updateRequest(player);

        return true;
    }
}
