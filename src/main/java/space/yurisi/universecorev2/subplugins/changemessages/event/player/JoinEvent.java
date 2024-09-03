package space.yurisi.universecorev2.subplugins.changemessages.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.yurisi.universecorev2.subplugins.changemessages.file.Config;

import java.util.List;

public final class JoinEvent implements Listener {

    private final Config config;

    public JoinEvent(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendTitle("§eWelcome to SpaceServer", "- Universe -");
        if (!player.hasPlayedBefore()) {
            event.joinMessage(getFirstJoinMessage(player));
            return;
        }
        event.joinMessage(getUserCustomJoinMessage(player));
    }

    private Component getFirstJoinMessage(Player player) {
        return Component.text("§a[入室] §a§l 初in §r§bの§c" + player.getName() + "§b様が§a§lオンライン§r§bになりました");
    }

    private Component getUserCustomJoinMessage(Player player) {
        if (!config.existsCustomJoinMessage(player)) {
            return Component.text(
                    "§a[入室] §c" + player.getName() + "§e様が§a§lオンライン§r§eになりました"
            );
        }
        return Component.text(
                "§a[入室] §c" + config.getCustomJoinMessage(player)
        );
    }
}
