package space.yurisi.universecorev2.event.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

public class EntityHangingBreakEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onHanging(HangingBreakByEntityEvent event) {
        if(event.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION){
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.ARMOR_STAND) {
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ||
                    event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                event.setCancelled(true);
            }
        }
    }
}