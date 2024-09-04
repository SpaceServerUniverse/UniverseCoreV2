package space.yurisi.universecorev2.subplugins.universeland.event.block;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import space.yurisi.universecorev2.exception.LandNotFoundException;
import space.yurisi.universecorev2.subplugins.universeland.UniverseLand;
import space.yurisi.universecorev2.subplugins.universeland.manager.LandDataManager;
import space.yurisi.universecorev2.subplugins.universeland.store.LandData;
import space.yurisi.universecorev2.subplugins.universeland.utils.BoundingBox;

public class PlaceEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent event) throws LandNotFoundException {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        LandDataManager landDataManager = LandDataManager.getInstance();
        BoundingBox bb = new BoundingBox(block.getX(), block.getZ(), block.getX(), block.getZ(), block.getWorld().getName());

        if(!landDataManager.canAccess(player, bb)){
            event.setCancelled(true);

            LandData data = landDataManager.getLandData(bb);

            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(data.getOwnerUUID());
            player.sendActionBar(Component.text("この土地は" + p.getName() + "によって保護されています"));
        }
    }
}
