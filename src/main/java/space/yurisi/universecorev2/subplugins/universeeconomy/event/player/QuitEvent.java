package space.yurisi.universecorev2.subplugins.universeeconomy.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UniverseEconomyAPI.getInstance().flushPlayer(event.getPlayer());
    }
}
