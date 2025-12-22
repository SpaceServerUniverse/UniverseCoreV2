package space.yurisi.universecorev2.subplugins.universeland.event.entity;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand; // 追加
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import space.yurisi.universecorev2.subplugins.universeland.manager.LandDataManager;
import space.yurisi.universecorev2.subplugins.universeland.store.LandData;
import space.yurisi.universecorev2.subplugins.universeland.utils.BoundingBox;

public class DamageEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();

        if (!(damager instanceof Player player)) return;

        if (!(entity instanceof ItemFrame) && !(entity instanceof ArmorStand)) return;

        LandDataManager landDataManager = LandDataManager.getInstance();

        Location loc = entity.getLocation();
        BoundingBox bb = new BoundingBox(loc.getBlockX(), loc.getBlockZ(), loc.getBlockX(), loc.getBlockZ(), loc.getWorld().getName());

        if (landDataManager.canAccess(player, bb)) return;

        event.setCancelled(true);
        LandData data = landDataManager.getLandData(bb);
        if (data != null) {
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(data.getOwnerUUID());
            player.sendActionBar(Component.text("この土地は" + p.getName() + "によって保護されています"));
        }
    }
}