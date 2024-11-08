package space.yurisi.universecorev2.event.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class ChangeBlockEvent  implements Listener {

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if(event.getEntity().getType() == EntityType.ENDERMAN) {
            event.setCancelled(true);
        }
    }
}
