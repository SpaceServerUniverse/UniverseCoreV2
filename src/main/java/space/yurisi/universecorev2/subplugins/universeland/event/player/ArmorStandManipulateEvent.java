package space.yurisi.universecorev2.subplugins.universeland.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import space.yurisi.universecorev2.exception.LandNotFoundException;
import space.yurisi.universecorev2.subplugins.universeland.manager.LandDataManager;
import space.yurisi.universecorev2.subplugins.universeland.store.LandData;
import space.yurisi.universecorev2.subplugins.universeland.utils.BoundingBox;

public class ArmorStandManipulateEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBucketEmpty(PlayerArmorStandManipulateEvent event) throws LandNotFoundException {
        Player player = event.getPlayer();
        Location location = event.getRightClicked().getLocation();

        LandDataManager landDataManager = LandDataManager.getInstance();
        BoundingBox bb = new BoundingBox(location.getBlockX(), location.getBlockZ(), location.getBlockX(), location.getBlockZ(), location.getWorld().getName());

        if (!landDataManager.canAccess(player, bb)) {
            event.setCancelled(true);

            LandData data = landDataManager.getLandData(bb);

            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(data.getOwnerUUID());
            player.sendActionBar(Component.text("この土地は" + p.getName() + "によって保護されています"));
        }
    }
}

