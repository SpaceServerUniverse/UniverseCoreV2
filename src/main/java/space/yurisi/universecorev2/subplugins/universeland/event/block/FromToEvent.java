package space.yurisi.universecorev2.subplugins.universeland.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import space.yurisi.universecorev2.exception.LandNotFoundException;
import space.yurisi.universecorev2.subplugins.universeland.manager.LandDataManager;
import space.yurisi.universecorev2.subplugins.universeland.store.LandData;
import space.yurisi.universecorev2.subplugins.universeland.utils.BoundingBox;

public class FromToEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFromTo(BlockFromToEvent event) throws LandNotFoundException {
        Block block = event.getBlock();
        Block to = event.getToBlock();

        LandData data = LandDataManager.getInstance().getLandData(new BoundingBox(to.getX(), to.getZ(), to.getX(), to.getZ(), to.getWorld().getName()));

        if (data != null) event.setCancelled(true);
    }
}
