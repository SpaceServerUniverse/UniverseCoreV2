package space.yurisi.universecorev2.subplugins.universejob.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.universejob.menu.ChangeJobMenu;

public class JobCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        ChangeJobMenu changeJobMenu = new ChangeJobMenu();
        changeJobMenu.sendMenu(player);

        return true;
    }
}
