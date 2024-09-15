package space.yurisi.universecorev2.subplugins.universeutilcommand.suicide;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class suCommand implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return false;

        }

        player.setHealth(0.0);
        Bukkit.broadcast(getMessage(player));

        return true;
    }

    private int getRandom(Component[] components){
        Random rnd = new Random();
        return rnd.nextInt(components.length);
    }

    private Component getMessage(Player player) {
        Component[] messages = new Component[]{
                Component.text("§a §l[死亡管理AI]§b " + player.getName() + "§a は消滅した"),
                Component.text("§a §l[死亡管理AI]§b " + player.getName() + "§a は存在がなくなった"),
                Component.text("§a §l[死亡管理AI]§b " + player.getName() + "§a はちりになった"),
                Component.text("§a §l[死亡管理AI]§b " + player.getName() + "§a は星になった")
        };
        int i = this.getRandom(messages);
        return messages[i];
    }
}
