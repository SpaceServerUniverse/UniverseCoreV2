package space.yurisi.universecorev2.event.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

public class PortalEvent implements Listener {

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event){
        event.setCancelled(true);
    }
}
