package space.yurisi.universecorev2.subplugins.autocollect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.autocollect.data.AutoCollectMap;
import space.yurisi.universecorev2.subplugins.autocollect.event.AutoCollectListener;
import space.yurisi.universecorev2.utils.Message;

public class AutoCollectCommand implements CommandExecutor {

    public static final String ACCommand = "[収集AI]";

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player) {
            AutoCollectMap.getInstance().toggleAutoCollect(player);
            Message.sendSuccessMessage(player, ACCommand, "§a自動収集モードを[ §c§l"+(AutoCollectMap.getInstance().isAutoCollect(player) ? "ON":"OFF")+" §a]に変更しました");
            return true;
        }
        return false;
    }
}
