package space.yurisi.universecorev2.subplugins.suicide.command;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class suCommand implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        /*
        Player player = (Player) commandSender;
        EntityDamageEvent event = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.SUICIDE, 100);
        player.setLastDamageCause(event);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return true;
        player.setHealth(0);
         */

        return true;
    }
}
