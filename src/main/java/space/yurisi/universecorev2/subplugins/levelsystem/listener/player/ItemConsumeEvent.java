package space.yurisi.universecorev2.subplugins.levelsystem.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;

public class ItemConsumeEvent implements Listener {

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event){
        if(event.isCancelled()){
            return;
        }
        Player player = event.getPlayer();
        LevelSystemAPI api = LevelSystemAPI.getInstance();

        try {
            api.addExp(player, 3);
        }catch (PlayerDataNotFoundException ignored){
        }
    }
}
