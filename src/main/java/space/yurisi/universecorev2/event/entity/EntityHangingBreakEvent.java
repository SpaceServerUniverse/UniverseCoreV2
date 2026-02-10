package space.yurisi.universecorev2.event.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityHangingBreakEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onHanging(HangingBreakByEntityEvent event) {
        event.setCancelled(event.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.ARMOR_STAND) {
            DamageCause damageCause = event.getCause();
            // エンティティへのダメージソースが，エンティティかブロック起因だった場合はキャンセルする
            event.setCancelled(damageCause == DamageCause.ENTITY_EXPLOSION || damageCause == DamageCause.BLOCK_EXPLOSION);
        }
    }
}
