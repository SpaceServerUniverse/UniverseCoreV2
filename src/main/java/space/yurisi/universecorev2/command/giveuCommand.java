package space.yurisi.universecorev2.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.List;

public class giveuCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        int level = 1;

        if(args.length < 1){
            Message.sendErrorMessage(player,"[管理AI]", "/giveu id level");
        }

        if(args.length >= 2){
            try {
                level = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignored){
            }
        }

        CustomItem item = UniverseItem.getItem(args[0]);
        if(item == null){
            Message.sendErrorMessage(player,"[管理AI]", "idが存在しませんでした。");
            return false;
        }

        if(item.isLevelingItem()){
            try {
                ItemStack itemStack = item.getItemFromLevel(level);
                player.getInventory().addItem(itemStack);
            } catch (CustomItemLevelNotFoundException e) {
                Message.sendErrorMessage(player, "[管理AI]", "レベルが存在しませんでした。");
                return false;
            }
        } else {
            ItemStack itemStack = item.getItem();
            player.getInventory().addItem(itemStack);
        }



        Message.sendSuccessMessage(player, "[管理AI]", item.getName() + "を与えました！");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("mywarp") || args.length != 1) {
            return null;
        }

        String input = args[0];
        List<String> options = List.of(UniverseItem.getItemIds());

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
