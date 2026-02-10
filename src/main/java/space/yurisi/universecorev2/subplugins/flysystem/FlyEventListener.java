package space.yurisi.universecorev2.subplugins.flysystem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class FlyEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        event.getPlayer().setAllowFlight(false);
        event.getPlayer().setFlying(false);
    }
}
