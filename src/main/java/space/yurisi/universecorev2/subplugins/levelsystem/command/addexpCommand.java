package space.yurisi.universecorev2.subplugins.levelsystem.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;

public class addexpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length != 2){
            return false;
        }

        Player to_player = Bukkit.getPlayerExact(args[0]);

        if(to_player == null){
            return false;
        }

        try {
            int exp = Integer.parseInt(args[1]);
            LevelSystemAPI.getInstance().addExp(to_player, exp);
            sender.sendMessage(to_player.getName() + "に" + exp + "EXP与えました。");
        } catch (NumberFormatException exception){
            sender.sendMessage("経験値は数値で入力する必要があります。");
        } catch (PlayerDataNotFoundException e) {
            sender.sendMessage("プレイヤーデータが見つかりません。プレイヤーがオンラインである必要があります。");
        }

        return true;
    }
}
