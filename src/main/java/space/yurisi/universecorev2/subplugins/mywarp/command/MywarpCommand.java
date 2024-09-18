package space.yurisi.universecorev2.subplugins.mywarp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;


import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.mywarp.command.subcommand.*;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp_menu.MywarpInventoryMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MywarpCommand implements CommandExecutor, TabCompleter {

    private UniverseCoreAPIConnector connector;

    public MywarpCommand(UniverseCoreAPIConnector connector) {
        this.connector = connector;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if(!(sender instanceof Player)){
            return false;
        }

        if(args.length == 0){
            MywarpInventoryMenu menu = new MywarpInventoryMenu(connector);
            menu.sendMenu((Player) sender);
            return false;
        }

        switch (args[0]){
            case "list":
                new listSubCommand().execute(connector, sender, args);
                break;
            case "add":
                new addSubCommand().execute(connector, sender, args);
                break;
            case "del":
                new delSubCommand().execute(connector, sender, args);
                break;
            case "tp":
                new tpSubCommand().execute(connector, sender, args);
                break;
            case "visit":
                new visitSubCommand().execute(connector, sender, args);
                break;
            case "visitlist":
                new visitlistSubCommand().execute(connector, sender, args);
                break;
            // それ以外のコマンド引数ではヘルプを表示する
            default:
                String[] helpMessage = """
            §6-- Mywarp Help --
            ☆ §b全てのコマンドは "/mw" でも実行できます ☆
               §7/mywarp : Mywarpのメニューを開きます
               §7/mywarp list : ワープポイントの一覧を表示します
               §7/mywarp add <ワープ名> <公開可否:する, しない> : ワープポイントを追加します
               §7/mywarp del <ワープ名> : 指定したワープポイントを削除します
               §7/mywarp tp <ワープ名> : 指定したワープポイントにテレポートします
               §7/mywarp visit <プレイヤー名> <ワープ名> : 指定したプレイヤーの公開ワープポイントにテレポートできます
               §7/mywarp visitlist <プレイヤー名> : 指定したプレイヤーの公開ワープポイントの一覧を表示します
               §7/mywarp help : このヘルプを表示します
            """.split("\n");
                sender.sendMessage(helpMessage);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("mywarp") || args.length != 1) {
            return null;
        }

        String input = args[0];
        List<String> options = Arrays.asList("list", "add", "del", "tp", "visit", "visitlist", "help");

        if (input.isEmpty()) {
            return options;
        }

        // 入力に基づいて候補を絞り込む
        List<String> matchedOptions = new ArrayList<>();
        for (String option : options) {
            if (option.startsWith(input)) {
                matchedOptions.add(option);
            }
        }

        return matchedOptions.isEmpty() ? null : matchedOptions;
    }



}
