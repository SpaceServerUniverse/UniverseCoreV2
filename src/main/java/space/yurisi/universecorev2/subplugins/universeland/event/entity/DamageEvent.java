package space.yurisi.universecorev2.subplugins.universeland.event.entity;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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

        if(!(damager instanceof Player player)) return;
        if (!(event.getEntity() instanceof ItemFrame itemFrame)) return;

        LandDataManager landDataManager = LandDataManager.getInstance();
        BoundingBox bb = new BoundingBox((int) itemFrame.getX(), (int) itemFrame.getZ(), (int) itemFrame.getX(), (int) itemFrame.getZ(), itemFrame.getWorld().getName());

        if (landDataManager.canAccess(player, bb)) return;

        event.setCancelled(true);

        LandData data = landDataManager.getLandData(bb);

        OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(data.getOwnerUUID());
        player.sendActionBar(Component.text("この土地は" + p.getName() + "によって保護されています"));
    }
}
