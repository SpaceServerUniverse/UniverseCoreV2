package space.yurisi.universecorev2.subplugins.changemessages.event.entity;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import space.yurisi.universecorev2.subplugins.changemessages.message.event.entity_damage_by_entity.PlayerDeathEntityByEntityEventMessage;

public final class DamageByEntityEvent implements Listener {

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event){
        Entity player = event.getEntity();
        Entity damager = event.getDamager();

        if(!(player instanceof Player)) {
            return;
        }

        if(damager instanceof Player){
            return;
        }

        double health = ((Player) player).getHealth() - event.getFinalDamage();
        if(health > 0){
            return;
        }

        if(damager instanceof Projectile){
            ProjectileSource shooter = ((Projectile) damager).getShooter();
            if(!(shooter instanceof Entity)){
                 return;
            }
            if(shooter instanceof Player){
                return;
            }
            damager = (Entity) shooter;
        }


        PlayerDeathEntityByEntityEventMessage event_message = new PlayerDeathEntityByEntityEventMessage((Player) player, damager);
        Component message = event_message.getMessage();
        Bukkit.broadcast(message);
    }
}
