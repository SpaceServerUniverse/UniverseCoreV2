package space.yurisi.universecorev2.subplugins.tppsystem.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.receive_request_menu.ReceiveRequestInventoryMenu;

import java.util.List;
import java.util.UUID;

public class listSubCommand implements TPPSubCommand {

    public boolean execute(UniverseCoreAPIConnector connector, RequestManager requestManager, CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        List<UUID> requests = requestManager.getRequest(player);
        if (!requestManager.hasReceivedRequest(player)) {
            Message.sendNormalMessage(player, "[テレポートAI]", "現在受信しているテレポート申請はありません．");
            return true;
        }

        ReceiveRequestInventoryMenu menu = new ReceiveRequestInventoryMenu(requestManager, connector);
        menu.sendMenu(player);
        return true;
    }

}
