package space.yurisi.universecorev2.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.api.LuckPermsWrapper;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.LevellingCustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.ticket.GachaTicket;
import space.yurisi.universecorev2.item.ticket.GunTicket;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.List;

public class giveuCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!LuckPermsWrapper.isUserInAdminOrDevGroup(player)) {
            Message.sendErrorMessage(player, "[管理AI]", "このコマンドを実行する権限がありません。");
            return false;
        }

        String[] helpMessage = """
            §6-- Give Universe Item Help --
               §7/giveu add <ID> [レベル] [アイテム数] : 指定されたIDのアイテムをインベントリに追加します
               §7/giveu list : 入手可能なアイテムのリストを表示します
               §7/giveu ticket <gacha | gun> [枚数] : ガチャや銃のチケットを指定した枚数分インベントリに追加します
               §7/giveu help : このヘルプを表示します
            """.split("\n");

        if(args.length < 1) {
            sender.sendMessage(helpMessage);
            return false;
        }

        int level = 1;

        switch (args[0]) {
            case "add":
                if(args.length >= 3){
                    try {
                        level = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e){
                        Message.sendErrorMessage(player, "[管理AI]", "レベルは数値で指定してください。");
                        return false;
                    }
                }

                CustomItem item = UniverseItem.getItem(args[1]);
                if(item == null){
                    Message.sendErrorMessage(player,"[管理AI]", "指定されたIDにはアイテムが存在しません。\nヒント: /giveu list でアイテムのリストを確認できます。");
                    return false;
                }

                Inventory inv = player.getInventory();

                if (inv.firstEmpty() == -1) {
                    Message.sendErrorMessage(player, "[管理AI]", "インベントリに空きがありません。");
                    return false;
                }

                if(item instanceof LevellingCustomItem levellingItem){
                    try {
                        ItemStack itemStack = levellingItem.getItem(level);
                        inv.addItem(itemStack);
                    } catch (CustomItemLevelNotFoundException e) {
                        Message.sendErrorMessage(player, "[管理AI]", "指定されたアイテムにレベルは存在しません。");
                        return false;
                    }
                } else {
                    ItemStack itemStack = item.getItem();
                    player.getInventory().addItem(itemStack);
                }

                Message.sendSuccessMessage(player, "[管理AI]", item.getName() + "を与えました！");
                break;
            case "list":
                StringBuilder itemList = new StringBuilder("§6-- Universe Item List --\n");

                for (String itemId : UniverseItem.getItemIds()) {
                    itemList.append("§7").append(itemId).append("\n");
                }

                player.sendMessage(itemList.toString());
                break;
            case "ticket":
                int amount;

                if (args.length < 2) {
                    Message.sendErrorMessage(player, "[管理AI]", "チケットの種類をgachaかgunで指定してください。");
                    return false;
                }

                if(args.length < 3){
                    Message.sendErrorMessage(player, "[管理AI]", "枚数を指定してください。");
                    return false;
                }

                String ticketType = args[1];
                ItemStack ticket;
                if(ticketType.equalsIgnoreCase("gacha")){
                    ticket = UniverseItem.getItem(GachaTicket.id).getItem();
                } else if(ticketType.equalsIgnoreCase("gun")) {
                    ticket = UniverseItem.getItem(GunTicket.id).getItem();
                } else {
                    Message.sendErrorMessage(player, "[管理AI]", "チケットの種類をgachaかgunで指定してください。");
                    return false;
                }

                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    Message.sendErrorMessage(player, "[管理AI]", "枚数は数値で指定してください。");
                    return false;
                }

                if (player.getInventory().firstEmpty() == -amount) {
                    Message.sendErrorMessage(player, "[管理AI]", "インベントリに空きがありません。");
                    return false;
                }

                for (int i = 1; i <= amount; i++) {
                    player.getInventory().addItem(ticket);
                }

                Message.sendSuccessMessage(player, "[管理AI]", "チケットを" + amount + "枚与えました！");
                break;
            default:
                player.sendMessage(helpMessage);
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("giveu")) {
            return null;
        }

        List<String> commands = List.of("add", "list", "ticket", "help");
        List<String> options = List.of(UniverseItem.getItemIds());

        if (args.length == 1) {
            return commands;
        }

        if (args.length == 2 && !args[0].equalsIgnoreCase("add")) {
            return null;
        }

        List<String> matchedOptions = new ArrayList<>();
        for (String option : options) {
            if (option.startsWith(args[1])) {
                matchedOptions.add(option);
            }
        }

        return matchedOptions.isEmpty() ? null : matchedOptions;
    }

}
