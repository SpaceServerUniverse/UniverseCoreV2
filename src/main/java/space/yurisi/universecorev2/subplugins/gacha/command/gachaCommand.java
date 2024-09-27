package space.yurisi.universecorev2.subplugins.gacha.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.subplugins.gacha.core.event.SolarSystemEventGacha;
import space.yurisi.universecorev2.subplugins.gacha.menu.gacha_menu.GachaInventoryMenu;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class gachaCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if(args.length == 0) {
            new GachaInventoryMenu().sendMenu(player);
            return true;
        }

        int frequency = 1;

        if(args.length == 2){
            try {
                frequency = Integer.parseInt(args[1]);

                if (frequency <= 0) {
                    Message.sendErrorMessage(player, "[ガチャAI]", "引数が不正です。");
                    return true;
                }

                // 多分40回以上回されるとインベントリがフルになり得るので、それを防ぐための処理
                if (frequency >= 40) {
                    Message.sendErrorMessage(player, "[ガチャAI]", "一度に引ける回数は40回までです。");
                    return true;
                }

            } catch (NumberFormatException e) {
                Message.sendErrorMessage(player, "[ガチャAI]", "回数は数字で入力してください。");
                return true;
            }
        }

        switch (args[0]){
            case "solar_system":
                new SolarSystemEventGacha(player).turn(frequency);
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("gacha") || args.length != 1) {
            return null;
        }

        String input = args[0];
        List<String> options = Arrays.asList("solar_system");

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
