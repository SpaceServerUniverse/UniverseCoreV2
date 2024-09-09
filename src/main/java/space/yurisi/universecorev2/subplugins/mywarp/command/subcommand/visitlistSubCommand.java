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

public class visitlistSubCommand implements MywarpSubCommand {

    public boolean execute(UniverseCoreAPIConnector connector, CommandSender sender, String[] args){
        if(!(sender instanceof Player player)){
            Bukkit.getLogger().info("コマンドラインでは実行できません。");
            return false;
        }
        if(args.length < 2){
            player.sendMessage(MessageHelper.getErrorMessage("/mwvisitlist"));
            return true;
        }
        try {
            List<Mywarp> mywarpList = connector.getPublicMywarpListFromName(args[0]);
            // ワープポイントの一覧をフォームで表示
            int mwpage = (mywarpList.size() / 5) + 1;
            if(mywarpList.size() % 5 == 0){
                mwpage--;
            }
            int page = 0;
            if(args.length == 2){
                if(mwpage > 1){
                    player.sendMessage(MessageHelper.getSuccessMessage("/mywarp visit ページ番号(§b1~" + mwpage + "§2)でページを指定できます。"));
                }
            }else {
                page = Integer.parseInt(args[1]) - 1;
                if(page < 0){
                    player.sendMessage(MessageHelper.getErrorMessage("ページ番号は1以上で指定してください。"));
                    return true;
                }
                if (page >= mwpage) {
                    player.sendMessage(MessageHelper.getErrorMessage("ページが存在しません。§b" + mwpage + "§2ページ目までしかありません。"));
                    return true;
                }
            }
            player.sendMessage("§aワールド | ワープポイント名");
            int mywarp_i = 0;
            for(int i = 0; i < 5; i++){
                mywarp_i = i + (page * 5);
                if(mywarpList.size() <= mywarp_i){
                    break;
                }
                Mywarp mywarp = mywarpList.get(mywarp_i);
                player.sendMessage(mywarp.getWorld_name() + " | §6" + mywarp.getName());
            }
            player.sendMessage(MessageHelper.getSuccessMessage("ワープポイント一覧 §b" + (page + 1) + "/" + mwpage + "§2ページ目を表示中"));

        } catch (UserNotFoundException e) {
            player.sendMessage(MessageHelper.getErrorMessage("ユーザーが見つかりませんでした。"));
        } catch (MywarpNotFoundException e) {
            player.sendMessage(MessageHelper.getErrorMessage("公開ワープポイントが見つかりませんでした。"));
        }

        return true;
    }
}
