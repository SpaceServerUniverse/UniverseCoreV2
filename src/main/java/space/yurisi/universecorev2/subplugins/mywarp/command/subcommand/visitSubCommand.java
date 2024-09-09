package space.yurisi.universecorev2.subplugins.mywarp.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.mywarp.utils.MessageHelper;

import java.util.List;

public class visitSubCommand implements MywarpSubCommand {

    public boolean execute(UniverseCoreAPIConnector connector, CommandSender sender, String[] args){
        if(!(sender instanceof Player player)){
            Bukkit.getLogger().info("コマンドラインでは実行できません。");
            return false;
        }

        if(args.length <= 2){
            player.sendMessage("/mwvisit <プレイヤー名> <ワープ名>");
            return false;
        }

        try {
            List<Mywarp> data = connector.getPublicMywarpListFromName(args[0]);
            // ワープポイントの一覧をフォームで表示
            // デバッグ用
            // TODO: クエリから取得
            for(Mywarp mywarp : data){
                if(mywarp.getName().equals(args[1])){
                    connector.teleportMywarp(player, mywarp);
                    player.sendMessage(MessageHelper.getSuccessMessage("§b" + args[1] + "§2さんの§6" + args[2] + " §2にワープしました。"));
                    return true;
                }
            }
            player.sendMessage(MessageHelper.getErrorMessage("ワープポイントが見つかりませんでした。"));

        } catch (UserNotFoundException e) {
            player.sendMessage(MessageHelper.getErrorMessage("ユーザーが見つかりませんでした。"));
        } catch (MywarpNotFoundException e) {
            player.sendMessage(MessageHelper.getErrorMessage("公開ワープポイントが見つかりませんでした。"));
        }

        return true;
    }
}
