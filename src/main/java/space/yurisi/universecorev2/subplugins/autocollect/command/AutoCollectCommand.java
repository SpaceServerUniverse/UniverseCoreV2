package space.yurisi.universecorev2.subplugins.autocollect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.autocollect.event.Listener;
import space.yurisi.universecorev2.utils.Message;

public class AutoCollectCommand implements CommandExecutor {

    public static final String ACCommand = "[収集AI]";

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player) {
            Listener.getInstance().toggleCollect(player);
            Message.sendSuccessMessage(player, ACCommand, "§a自動収集モードを[ §c§l"+(Listener.getInstance().isCollect(player) ? "ON":"OFF")+" §a]に変更しました");
            return true;
        }
        return false;
    }
}
