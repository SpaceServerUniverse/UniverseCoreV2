package space.yurisi.universecorev2.subplugins.universeslot.listener.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;
import space.yurisi.universecorev2.utils.Message;

public class SlotPlayerMoveProtect implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().equals(event.getTo())) {
            return;
        }

        PlayerStatusManager statusManager = UniverseSlot.getInstance().getPlayerStatusManager();
        Player player = event.getPlayer();

        if (!statusManager.hasPlayerSlotCore(player.getUniqueId())) {
            return;
        }

        // スロット中の移動をキャンセル
        event.setTo(event.getFrom());
        player.sendActionBar(Component.text("[スロットAI] スロット中は移動できません。"));
    }
}
