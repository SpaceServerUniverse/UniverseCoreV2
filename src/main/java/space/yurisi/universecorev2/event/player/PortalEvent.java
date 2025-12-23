package space.yurisi.universecorev2.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.Plugin;

public class PortalEvent implements Listener {

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        event.setCancelled(true);
    }


}
