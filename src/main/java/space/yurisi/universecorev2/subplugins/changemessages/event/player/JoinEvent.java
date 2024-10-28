package space.yurisi.universecorev2.subplugins.changemessages.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.changemessages.data.SuicidePlayerData;
import space.yurisi.universecorev2.subplugins.changemessages.file.Config;

import java.util.List;

public final class JoinEvent implements Listener {

    private final Config config;

    private final Plugin plugin;

    public JoinEvent(Config config, Plugin plugin) {
        this.config = config;
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SuicidePlayerData.getInstance().register(player);
        player.sendTitle("§eWelcome to SpaceServer", "- Universe -");
        if (!player.hasPlayedBefore()) {
            event.joinMessage(getFirstJoinMessage(player));
            return;
        }
        event.joinMessage(getUserCustomJoinMessage(player));


        if(player.getWorld().getName().equals("lobby")){
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setAllowFlight(true);
                }
            }.runTaskLater(plugin, 10);
        }
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
