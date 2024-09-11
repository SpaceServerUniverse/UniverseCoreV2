package space.yurisi.universecorev2.subplugins.signcommand.event;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (!(event.getClickedBlock().getState() instanceof Sign)) {
            return;
        }

        Sign sign = (Sign) event.getClickedBlock().getState();
        Player player = event.getPlayer();
        String[] command = sign.getLines();
        String execute = "";
        for (String line : command){
            execute += line;
        }


        if (execute.startsWith("##")) {
            player.performCommand(execute.substring(2));
        }
    }
}


