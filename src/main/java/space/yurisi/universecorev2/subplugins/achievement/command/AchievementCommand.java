package space.yurisi.universecorev2.subplugins.achievement.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.achievement.menu.AchievementMenu;

public class AchievementCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            AchievementMenu menu = new AchievementMenu();
            menu.sendMenu(player);
            return true;
        }
        return false;
    }
}
