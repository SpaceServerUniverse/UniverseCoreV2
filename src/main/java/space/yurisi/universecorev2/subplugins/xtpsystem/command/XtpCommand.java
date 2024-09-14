package space.yurisi.universecorev2.subplugins.xtpsystem.command;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.utils.Message;

public class XtpCommand implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }

        /* TODO: 1, 配列が3こ以下か確認, 2, x y z → 小数点ある数(doubleに変換可能)かの確認, 3, ワールドの名前がサーバーに存在するか確認 */

        if(args.length < 4) {
            Message.sendNormalMessage(player, "[テレポートAI]", "/xtp <x> <y> <z> <ワールド名> : 指定された座標にテレポートします");
            return false;
        }

        try {
            double x = Integer.parseInt(args[0]);
            double y = Integer.parseInt(args[1]);
            double z = Integer.parseInt(args[2]);

            World world = Bukkit.getServer().getWorld(args[3]);

            if(world == null){
                Message.sendErrorMessage(player, "[テレポートAI]", "ワールド名が存在しません。");
                return false;
            }

            Location location = new Location(world, x, y, z);

            player.teleport(location);
            Message.sendSuccessMessage(player, "[テレポートAI]", location + "にテレポート成功しました！");
        } catch (NumberFormatException e){
            Message.sendErrorMessage(player, "[テレポートAI]", "座標は数値で指定して下さい。");
            return false;
        }

        return true;
    }
}
