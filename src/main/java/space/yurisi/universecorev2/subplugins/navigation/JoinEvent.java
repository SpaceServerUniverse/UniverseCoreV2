package space.yurisi.universecorev2.subplugins.navigation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.yurisi.universecorev2.subplugins.navigation.file.Config;

public class JoinEvent implements Listener {

    private final Config config;

    public JoinEvent(Config config) {
        this.config = config;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("§6---☆ Welcome to SpaceServer Universe ☆---\n");
        player.sendMessage("§r§aWiki: " + config.getNavigation("wiki"));
        player.sendMessage("§r§aWeb: " + config.getNavigation("web"));
        player.sendMessage("§r§aDiscord: " + config.getNavigation("discord"));
    }
}
