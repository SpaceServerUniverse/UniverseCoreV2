package space.yurisi.universecorev2.command;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;
import space.yurisi.universecorev2.item.solar_system.SolarSystemSword;

public class lobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        World world = Bukkit.getServer().getWorld("lobby");
        player.teleport(new Location(world, 0, 0, 0));
        return true;
    }
}
