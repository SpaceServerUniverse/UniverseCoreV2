package space.yurisi.universecorev2.subplugins.universeslot.listener.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;

public class LogoutEvent implements Listener {

    private UniverseSlot main;
    public LogoutEvent(UniverseSlot main) {
        this.main = main;
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(player == null) {
            return;
        }
        if(main.getPlayerStatusManager().hasPlayerSlotCore(player.getUniqueId())){
            main.getPlayerStatusManager().getPlayerSlotCore(player.getUniqueId()).stopSlotMachine();
        }

    }
}
