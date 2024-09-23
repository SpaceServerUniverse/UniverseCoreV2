package space.yurisi.universecorev2.subplugins.levelaward.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.levelaward.menu.LevelMenu;

public class LevelAwardOpenMenu implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(Component.text("このコマンドはゲーム内で実行してください"));
            return false;
        }
        LevelMenu levelMenu = new LevelMenu();
        levelMenu.sendMenu(player);

        return true;
    }
}

