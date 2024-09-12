package space.yurisi.universecorev2.subplugins.universedice.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class UniverseDiceCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        if (args.length == 0 || args.length == 1) {
            sender.sendMessage("§a[DiceAI] §f/dice <最小値> <最大値> : 指定された範囲内の乱数を生成します");
            return false;
        }

        try {
            int min = Integer.parseInt(args[0]);
            int max = Integer.parseInt(args[1]);

            if (min > max) {
                sender.sendMessage("§c[DiceAI] §f最小値は最大値よりも小さくすることはできません");
                return false;
            }

            if (max > 10000) {
                sender.sendMessage("§c[DiceAI] §f最大値は10000以下にしてください");
                return false;
            }

            int result = new Random().nextInt((max - min) + 1) + min;
            sender.getServer().broadcast(Component.text("§a[DiceAI] §6" + sender.getName() + " が " + min + " から " + max + " の範囲でダイスを振りました。結果は " + result + " です"));
            return true;
        } catch (NumberFormatException e) {
            sender.sendMessage("§c[DiceAI] §f数値を指定してください");
            return false;
        }
    }
}
