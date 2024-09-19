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

public class denySubCommand implements TPPSubCommand {

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

        String playerName = targetPlayer.getName();
        targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        player.sendMessage("§6" + playerName + " §2のテレポート申請を拒否しました.");
        targetPlayer.sendMessage("§6" + player.getName() + " §2にテレポートを拒否されました.");

        requestManager.removeSearchReceiver(targetPlayer);
        requestManager.removeRequest(targetPlayer, player);

        requestManager.updateRequest(player);

        return true;
    }
}
