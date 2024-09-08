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
            return true;
        }

        /*sender.sendMessage("Mywarp Help");
        sender.sendMessage("/mywarp : このヘルプを表示します。<>内は必須で、()内は必須ではありません");
        sender.sendMessage("/mwlist : ワープポイントの一覧を表示します");
        sender.sendMessage("/mwadd <ワープ名> <公開可否:する, しない> : ワープポイントを追加します");
        sender.sendMessage("/mwdel <ワープ名> : 指定したワープポイントを削除します");
        sender.sendMessage("/mwtp <ワープ名> : 指定したワープポイントにテレポートします");
        sender.sendMessage("/mwvisit <プレイヤー名> <ワープ名> : 指定したプレイヤーの公開ワープポイントにテレポートできます");
        sender.sendMessage("/mwvisitlist <プレイヤー名> : 指定したプレイヤーの公開ワープポイントの一覧を表示します");
        */

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
            default:
                return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("mywarp") || args.length != 1) {
            return null;
        }

        String input = args[0];
        List<String> options = Arrays.asList("list", "add", "del", "tp", "visit", "visitlist");

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
