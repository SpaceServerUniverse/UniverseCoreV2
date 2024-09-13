package space.yurisi.universecorev2.event.block;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ExplosionPrimeEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPrime(org.bukkit.event.entity.ExplosionPrimeEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof TNTPrimed){
            event.setCancelled(true);
        }
    }
}
