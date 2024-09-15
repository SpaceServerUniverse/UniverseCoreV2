package space.yurisi.universecorev2.subplugins.customname.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.customname.menu.change_menu.ChangeNameAnvilMenu;

public class tagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        ChangeNameAnvilMenu menu = new ChangeNameAnvilMenu();
        menu.sendMenu((Player) sender);
        return true;
    }
}
