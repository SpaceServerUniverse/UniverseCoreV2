package space.yurisi.universecorev2.subplugins.birthdaycard.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.BirthdayCalendarMenu;

public class BirthdayCardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            //sender.sendMessage(BirthdayCard.PREFIX + "プレイヤー内で実行してね");
            return false;
        }
        if (args.length == 0) {
            return false;
        }
        BirthdayCalendarMenu menu = new BirthdayCalendarMenu();
        menu.sendMenu(player);

        return true;
    }
}
