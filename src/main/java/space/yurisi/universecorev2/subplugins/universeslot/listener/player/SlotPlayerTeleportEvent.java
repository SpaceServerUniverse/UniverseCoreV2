package space.yurisi.universecorev2.subplugins.universeslot.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.core.SlotCore;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;

public class SlotPlayerTeleportEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        PlayerStatusManager statusManager = UniverseSlot.getInstance().getPlayerStatusManager();
        Player player = event.getPlayer();

        if (!statusManager.hasPlayerSlotCore(player.getUniqueId())) {
            return;
        }

        // テレポート時にスロットを停止
        SlotCore slotCore = statusManager.getPlayerSlotCore(player.getUniqueId());
        if (slotCore != null) {
            slotCore.stopSlotMachine();
            statusManager.removePlayerSlotCore(player.getUniqueId());
        }
    }
}
