package space.yurisi.universecorev2.subplugins.navigation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.navigation.file.Config;
import space.yurisi.universecorev2.utils.Message;

public class NavigationCommand implements CommandExecutor {

    private final String key;
    private final Config config;

    public NavigationCommand(String key, Config config) {
        this.key = key;
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        String name = key.substring(0, 1).toUpperCase() + key.substring(1);
        player.sendMessage("§a" + name + ": " + config.getNavigation(key));
        return true;
    }

}
