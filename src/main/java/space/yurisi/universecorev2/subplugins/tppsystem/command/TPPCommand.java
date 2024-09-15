package space.yurisi.universecorev2.subplugins.tppsystem.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.tppsystem.command.subcommand.*;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.TPPSystemInventoryMenu;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.TPPSystem;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TPPCommand implements CommandExecutor, TabCompleter {

    private final RequestManager requestManager = new RequestManager();

    private final UniverseCoreAPIConnector connector;

    public TPPCommand(UniverseCoreAPIConnector connector){
        this.connector = connector;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if(!(sender instanceof Player)){
            return false;
        }

        if(args.length == 0){
            TPPSystemInventoryMenu menu = new TPPSystemInventoryMenu(this.requestManager, this.connector);
            menu.sendMenu((Player) sender);
            return true;
        }

        /*
        sender.sendMessage("TPP Help");
        sender.sendMessage("/tpp help : このヘルプを表示します。<>内は必須で、()内は必須ではありません");
        sender.sendMessage("/tpp send <プレイヤー名> : テレポート申請を送信します");
        sender.sendMessage("/tpp accept　<プレイヤー名> : テレポート申請を受け入れます");
        sender.sendMessage("/tpp deny　<プレイヤー名>  : テレポート申請を拒否します");
        */

//        switch (args[0]){
//            case "send":
//                new sendSubCommand().execute(sender, args);
//                break;
//            case "accept":
//                new acceptSubCommand().execute(sender, args);
//                break;
//            case "deny":
//                new denySubCommand().execute(sender, args);
//                break;
//            case "list":
//                new listSubCommand().execute(sender, args);
//                break;
//            default:
//                sender.sendMessage("TPP Help");
//                sender.sendMessage("<>内は必須で、()内は必須ではありません");
//                sender.sendMessage("/tpp send <プレイヤー名> : テレポート申請を送信します");
//                sender.sendMessage("/tpp accept　<プレイヤー名> : テレポート申請を受け入れます");
//                sender.sendMessage("/tpp deny　<プレイヤー名>  : テレポート申請を拒否します");
//                sender.sendMessage("/tpp list : テレポート申請の一覧を表示します");
//                return false;
//        }
//
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(!command.getName().equalsIgnoreCase("tpp") || args.length != 1){
            return null;
        }

        String input = args[0];
        List<String> subCommands = Arrays.asList("send", "accept", "deny", "list");

        if (input.isEmpty()) {
            return subCommands;
        }

        List<String> completions = new ArrayList<>();
        for (String subCommand : subCommands) {
            if (subCommand.startsWith(input)) {
                completions.add(subCommand);
            }
        }

        return completions.isEmpty() ? null : completions;
    }
}
