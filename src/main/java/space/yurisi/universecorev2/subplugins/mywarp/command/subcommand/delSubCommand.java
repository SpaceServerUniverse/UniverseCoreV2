package space.yurisi.universecorev2.subplugins.mywarp.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.mywarp.utils.MessageHelper;

public class delSubCommand implements MywarpSubCommand {

    public boolean execute(UniverseCoreAPIConnector connector, CommandSender sender, String[] args){
            if (!(sender instanceof Player player)) {
                Bukkit.getLogger().info("コマンドラインでは実行できません。");
                return false;
            }

            if(args.length < 2){
                player.sendMessage(MessageHelper.getErrorMessage("/mywarp del <ワープ名>"));
                return false;
            }
            try {
                Mywarp mywarp = connector.getMywarpFromName(player, args[0]);
                connector.deleteMywarp(mywarp);
                player.sendMessage(MessageHelper.getSuccessMessage("ワープポイント" + args[0] + "を削除しました。"));
            } catch (UserNotFoundException e) {
                player.sendMessage(MessageHelper.getErrorMessage("ユーザーデータが存在しないようです。管理者に報告してください。 コード-MWD1"));
            } catch (MywarpNotFoundException e) {
                player.sendMessage(MessageHelper.getErrorMessage("ワープポイントが見つかりませんでした。"));
            }

            return true;
        }
}
