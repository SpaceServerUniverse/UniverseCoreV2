package space.yurisi.universecorev2.subplugins.playerhead.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.playerhead.menu.PlayerHeadMenu;

public class PlayerHeadMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        PlayerHeadMenu menu = new PlayerHeadMenu();
        menu.sendMenu((Player) sender);

        return false;
    }
}
