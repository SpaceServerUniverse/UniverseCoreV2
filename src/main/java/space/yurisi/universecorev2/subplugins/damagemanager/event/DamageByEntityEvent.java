package space.yurisi.universecorev2.subplugins.damagemanager.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import space.yurisi.universecorev2.subplugins.damagemanager.utils.Config;

import java.util.List;

public class DamageByEntityEvent implements Listener {
    private final Config config;

    public DamageByEntityEvent(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Player player)) return;
        List<String> denyWorlds = this.config.getDenyWorlds();
        String world_name = player.getWorld().getName();
        if (!denyWorlds.contains(world_name)){
            e.setCancelled(true);
        }
    }
}
