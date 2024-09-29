package space.yurisi.universecorev2.subplugins.xtpsystem.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.xtpsystem.file.Config;
import space.yurisi.universecorev2.utils.Message;


public class XtpCommand implements CommandExecutor{

    private Config config;

    public XtpCommand(Config config){
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }

        if (args.length < 4) {
            Message.sendNormalMessage(player, "[テレポートAI]", "/xtp <x> <y> <z> <ワールド名> : 指定された座標にテレポートします");
            return false;
        }

        try {
            double x = Integer.parseInt(args[0]);
            double y = Integer.parseInt(args[1]);
            double z = Integer.parseInt(args[2]);

            World world = Bukkit.getServer().getWorld(args[3]);

            if (world == null) {
                Message.sendErrorMessage(player, "[テレポートAI]", "ワールド名が存在しません。");
                return false;
            }

            if(config.getDenyWorlds().contains(world.getName())){
                Message.sendErrorMessage(player, "[テレポートAI]", "このワールドにはワープできません。");
                return false;
            }

            WorldBorder border = world.getWorldBorder();

            Location location = new Location(world, x, y, z);

            if (!border.isInside(location)) {
                Message.sendErrorMessage(player, "[テレポートAI]", "指定された座標はボーダー外のためワープできません。");
                return false;
            }

            String xMessage = String.valueOf(location.getX());
            String yMessage = String.valueOf(location.getY());
            String zMessage = String.valueOf(location.getZ());
            String worldName = location.getWorld().getName();

            player.teleport(location);
            Message.sendSuccessMessage(player, "[テレポートAI]",
                    " x:" + xMessage +
                            " y:" + yMessage +
                            " z:" + zMessage +
                            " ワールド:" + worldName + "にテレポート成功しました！"
            );
        } catch (NumberFormatException e) {
            Message.sendErrorMessage(player, "[テレポートAI]", "座標は数値で指定して下さい。");
            return false;
        }

        return true;
    }
}
