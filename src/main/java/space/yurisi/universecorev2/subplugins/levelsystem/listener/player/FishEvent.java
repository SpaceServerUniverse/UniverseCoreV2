package space.yurisi.universecorev2.subplugins.levelsystem.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;

public class FishEvent implements Listener {

    @EventHandler
    public void onFish(PlayerFishEvent event){
        if (event.isCancelled()){
            return;
        }
        Player player = event.getPlayer();
        LevelSystemAPI api = LevelSystemAPI.getInstance();

        if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH){
            try {
                api.addExp(player, 70);
            }catch (PlayerDataNotFoundException ignored){
            }
        }
    }
}
