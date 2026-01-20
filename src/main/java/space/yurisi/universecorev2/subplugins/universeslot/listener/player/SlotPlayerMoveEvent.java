package space.yurisi.universecorev2.subplugins.universeslot.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.core.SlotCore;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;

public class SlotPlayerMoveEvent implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerToggleSneakEvent event) {
        PlayerStatusManager statusManager = UniverseSlot.getInstance().getPlayerStatusManager();
        Player player = event.getPlayer();

        if (!statusManager.hasPlayerSlotCore(player.getUniqueId())) {
            return;
        }

        // スロット中に椅子から立ち上がったら中断
        SlotCore slotCore = statusManager.getPlayerSlotCore(player.getUniqueId());
        if (slotCore != null) {
            slotCore.stopSlotMachine();
            statusManager.removePlayerSlotCore(player.getUniqueId());
        }

        if(statusManager.hasFlag(player.getUniqueId(), PlayerStatusManager.ON_FREEZE_MODE)){
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}
