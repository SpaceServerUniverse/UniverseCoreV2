package space.yurisi.universecorev2.subplugins.levelsystem.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;
import space.yurisi.universecorev2.utils.Message;

public class addexpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 2) {
            Message.sendNormalMessage((Player) sender, "[XP管理AI]", "/addexp <プレイヤー名> <経験値>");
            return false;
        }

        Player to_player = Bukkit.getPlayerExact(args[0]);

        if (to_player == null) {
            Message.sendErrorMessage((Player) sender, "[XP管理AI]", "プレイヤーが見つかりませんでした。");
            return false;
        }

        try {
            int exp = Integer.parseInt(args[1]);
            LevelSystemAPI.getInstance().addExp(to_player, exp);
            Message.sendNormalMessage(to_player, "[XP管理AI]", to_player.getName() + "に" + exp + "EXP与えました。");
        } catch (NumberFormatException exception){
            Message.sendErrorMessage(to_player, "[XP管理AI]", "経験値は数値で入力する必要があります。");
            return false;
        } catch (PlayerDataNotFoundException e) {
            Message.sendErrorMessage(to_player, "[XP管理AI]", "プレイヤーデータが見つかりません。プレイヤーがオンラインである必要があります");
            return false;
        }

        return true;
    }
}
