package space.yurisi.universecorev2.subplugins.universeutilcommand.dice;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.utils.Message;

import java.util.Random;

public class DiceCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length == 0 || args.length == 1) {
            Message.sendNormalMessage(player, "[DiceAI]", "/dice <最小値> <最大値> : 指定された範囲内の乱数を生成します");
            return false;
        }

        try {
            int min = Integer.parseInt(args[0]);
            int max = Integer.parseInt(args[1]);

            if (min > max) {
                Message.sendWarningMessage(player, "[DiceAI]", "最小値は最大値より小さくしてください");
                return false;
            }

            if (max > 10000) {
                Message.sendWarningMessage(player, "[DiceAI]", "最大値は10000以下にしてください");
                return false;
            }

            int result = new Random().nextInt((max - min) + 1) + min;
            sender.getServer().broadcast(Component.text("§d[DiceAI] §6" + sender.getName() + "§d が §6" + min + " から " + max + " §dの範囲でダイスを振りました。結果は §6" + result + " §dです"));
            Message.sendNormalMessage(player, "[DiceAI]", "§nもう一度引く場合はクリックしてください", ClickEvent.runCommand("/dice " + min + " " + max), "クリックすると再度 §d[" + min + " 〜 " + max + "] §fの範囲でダイスを振ります");

            return true;
        } catch (NumberFormatException e) {
            Message.sendErrorMessage(player, "[DiceAI]", "数値を指定してください");
            return false;
        }
    }
}
