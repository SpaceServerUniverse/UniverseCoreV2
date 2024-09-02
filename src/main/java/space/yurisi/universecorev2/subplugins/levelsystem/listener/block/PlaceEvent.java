package space.yurisi.universecorev2.subplugins.levelsystem.listener.block;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;

public class PlaceEvent implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if(event.isCancelled()){
            return;
        }

        Player player = event.getPlayer();

        if(!player.getGameMode().equals(GameMode.SURVIVAL)){
            return;
        }

        try {
            LevelSystemAPI.getInstance().addExp(player, 1);
        } catch (PlayerDataNotFoundException exception) {
        }
    }
}
