package space.yurisi.universecorev2.subplugins.receivebox.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.utils.Message;

public class addreceiveCommand  implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if(args.length < 2){
            Message.sendErrorMessage(player, "[管理AI]", "/addreceive player description expire_datetime(YYYY-mm-dd HH:ii:ss)");
            return false;
        }
        return true;
    }
}
