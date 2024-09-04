package space.yurisi.universecorev2.subplugins.mywarp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.MywarpInventoryMenu;

public class MywarpHelpCommand  extends MywarpBaseCommand{

    public MywarpHelpCommand(UniverseCoreAPIConnector connector) {
        super(connector);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if(!(sender instanceof Player)){
            return false;
        }

        /*if(args.length != 0){
            return false;
        }

        sender.sendMessage("Mywarp Help");
        sender.sendMessage("/mywarp : このヘルプを表示します。<>内は必須で、()内は必須ではありません");
        sender.sendMessage("/mwlist : ワープポイントの一覧を表示します");
        sender.sendMessage("/mwadd <ワープ名> <公開可否:する, しない> : ワープポイントを追加します");
        sender.sendMessage("/mwdel <ワープ名> : 指定したワープポイントを削除します");
        sender.sendMessage("/mwtp <ワープ名> : 指定したワープポイントにテレポートします");
        sender.sendMessage("/mwvisit <プレイヤー名> <ワープ名> : 指定したプレイヤーの公開ワープポイントにテレポートできます");
        sender.sendMessage("/mwvisitlist <プレイヤー名> : 指定したプレイヤーの公開ワープポイントの一覧を表示します");
        */

        MywarpInventoryMenu menu = new MywarpInventoryMenu();
        menu.sendMenu((Player) sender);
        return true;
    }
}
