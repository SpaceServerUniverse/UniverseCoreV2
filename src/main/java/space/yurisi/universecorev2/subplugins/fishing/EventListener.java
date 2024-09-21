package space.yurisi.universecorev2.subplugins.fishing;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.logging.Logger;

public class EventListener implements Listener {

    private final Logger logger;

    public EventListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if(event.getState() != PlayerFishEvent.State.CAUGHT_FISH){
            return;
        }

        Item itemEntity = (Item) event.getCaught();
        assert itemEntity != null;

        
    }
}
