package space.yurisi.universecorev2.subplugins.mywarp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;

public class MywarpTeleportCommand extends MywarpBaseCommand {

    public MywarpTeleportCommand(UniverseCoreAPIConnector connector) {
        super(connector);
    }

    @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
            if (!(sender instanceof Player player)) {
                return false;
            }
            if(args.length == 0){
                player.sendMessage(getErrorMessage("ワープポイント名を入力してください。"));
                return true;
            }
            try {
                Mywarp mywarp = connector.getMywarpFromName(player, args[0]);
                connector.teleportMywarp(player, mywarp);
                player.sendMessage(getSuccessMessage("§6" + args[0] + " §2にワープしました。"));
            } catch (UserNotFoundException e) {
                player.sendMessage(getErrorMessage("ユーザーデータが存在しないようです。管理者に報告してください。 コード-MWT1"));
            } catch (MywarpNotFoundException e) {
                player.sendMessage(getErrorMessage("ワープポイントが見つかりませんでした。"));
            }

            return true;
        }
}
