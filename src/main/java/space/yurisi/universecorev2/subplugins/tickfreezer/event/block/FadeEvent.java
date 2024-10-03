package space.yurisi.universecorev2.subplugins.tickfreezer.event.block;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import space.yurisi.universecorev2.subplugins.tickfreezer.file.Config;
import space.yurisi.universecorev2.subplugins.tickfreezer.utils.Coral;

public class FadeEvent implements Listener {
    private final Config config;

    public FadeEvent(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        if (Coral.isCoral(event.getBlock().getType())) {
            if (config.getDenyWorlds().contains(event.getBlock().getWorld().getName())) {
                event.setCancelled(true);
            }
        }
    }
}
