package space.yurisi.universecorev2.subplugins.rankcounter.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.CounterModelManager;

public class QuitEvent implements Listener {

    private CounterModelManager manager;

    public QuitEvent(CounterModelManager manager){
        this.manager = manager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        
        if(manager.exists(player)){
            manager.get(player).saveAll();
            manager.unregister(player);
        }
    }
}
