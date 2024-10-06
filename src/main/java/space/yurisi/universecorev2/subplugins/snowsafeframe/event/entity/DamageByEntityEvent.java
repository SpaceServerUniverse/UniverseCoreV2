package space.yurisi.universecorev2.subplugins.snowsafeframe.event.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityEvent implements Listener {
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ITEM_FRAME) {
            if (event.getDamager().getType() == EntityType.SNOWBALL) {
                event.setCancelled(true);
            }
        }
    }
}
