package space.yurisi.universecorev2.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.utils.Message;

public class flatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        World world = Bukkit.getServer().getWorld("flat");
        player.teleport(new Location(world, 100, -61, 100));
        Message.sendSuccessMessage(player, "[テレポートAI]", "フラットにテレポートしました");

        return true;
    }
}