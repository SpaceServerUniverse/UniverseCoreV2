package space.yurisi.universecorev2.subplugins.mywarp.command.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.mywarp.command.MywarpBaseCommand;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;

public class addSubCommand extends MywarpBaseCommand implements MywarpSubCommand {

    public boolean execute(UniverseCoreAPIConnector connector, CommandSender sender, String[] args){
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length < 3) {
            return false;
        }

        if(args[1].length() > 20){
            sender.sendMessage(getErrorMessage("名前は空白なしの20文字以下にしてください。"));
            return true;
        }
        if(connector.isDenyWorld(player.getWorld().getName())){
            sender.sendMessage(getErrorMessage("このワールドではワープポイントを作成できません。"));
            return true;
        }

        try{
            boolean isExist = connector.isExistsMywarpName(player, args[1]);
            if(isExist){
                sender.sendMessage(getErrorMessage("その名前のワープポイントは既に存在します。"));
                return false;
            }
        } catch (MywarpNotFoundException e) {
        } catch (UserNotFoundException e) {
            sender.sendMessage(getErrorMessage("ユーザーデータが存在しないようです。管理者に報告してください。 コード-MWC1"));
            return false;
        }

        boolean isPrivate;
        if(args[2].equals("true") || args[2].equals("True") || args[2].equals("TRUE") || args[2].equals("する")) {
            isPrivate = false;
        }else if(args[2].equals("false") || args[2].equals("False") || args[2].equals("FALSE") || args[2].equals("しない")) {
            isPrivate = true;
        }else{
            sender.sendMessage(getErrorMessage("公開可否は[する(true)]か[しない(false)]で指定してください。"));
            return false;
        }

        connector.createMywarp(player, args[1], isPrivate);
        String isPrivateString = isPrivate ? "非公開" : "公開";
        player.sendMessage(getSuccessMessage("ワープポイント" + args[1] + "を" + isPrivateString + "で作成しました。"));

        return true;
    }
}
