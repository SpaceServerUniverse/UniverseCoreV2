package space.yurisi.universecorev2.subplugins.loginbonus.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.loginbonus.menu.LoginBonusCalendar;
import space.yurisi.universecorev2.subplugins.mywarp.command.subcommand.*;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp_menu.MywarpInventoryMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginBonusCommand implements CommandExecutor, TabCompleter {

    public LoginBonusCommand() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if(!(sender instanceof Player)){
            return false;
        }

        if(args.length == 0){
            LoginBonusCalendar menu = new LoginBonusCalendar();
            menu.sendMenu((Player) sender);
            return false;
        }

        switch (args[0]){
            case "receive":

                break;
            // それ以外のコマンド引数ではヘルプを表示する
            default:
                String[] helpMessage = """
                 /loginbonus ログインボーナス画面を開く
                 /loginbonus receive ログインボーナスを受け取る
            """.split("\n");
                sender.sendMessage(helpMessage);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("loginbonus")) {
            return null;
        }

        if (args.length == 1) {
            String input = args[0].toLowerCase();

            List<String> options = List.of("receive");

            List<String> result = new ArrayList<>();
            for (String option : options) {
                if (option.startsWith(input)) {
                    result.add(option);
                }
            }

            return result;
        }

        return List.of();
    }
}
