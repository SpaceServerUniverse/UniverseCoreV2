package space.yurisi.universecorev2.subplugins.mywarp.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.mywarp.utils.MessageHelper;

public class tpSubCommand implements MywarpSubCommand {

    public boolean execute(UniverseCoreAPIConnector connector, CommandSender sender, String[] args){
            if (!(sender instanceof Player player)) {
                Bukkit.getLogger().info("コマンドラインでは実行できません。");
                return false;
            }
            if(args.length == 0){
                player.sendMessage("/mwtp <ワープ名>");
                return true;
            }
            try {
                Mywarp mywarp = connector.getMywarpFromName(player, args[1]);
                connector.teleportMywarp(player, mywarp);
                player.sendMessage(MessageHelper.getSuccessMessage("§6" + args[1] + " §2にワープしました。"));
            } catch (UserNotFoundException e) {
                player.sendMessage(MessageHelper.getErrorMessage("ユーザーデータが存在しないようです。管理者に報告してください。 コード-MWT1"));
            } catch (MywarpNotFoundException e) {
                player.sendMessage(MessageHelper.getErrorMessage("ワープポイントが見つかりませんでした。"));
            }

            return true;
        }
}
