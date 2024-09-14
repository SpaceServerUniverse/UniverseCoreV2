package space.yurisi.universecorev2.event.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EExplodeEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPrime(EntityExplodeEvent event){
            event.blockList().clear();
    }
}
