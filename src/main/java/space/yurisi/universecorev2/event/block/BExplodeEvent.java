package space.yurisi.universecorev2.event.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class BExplodeEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onExplode(BlockExplodeEvent event){
        event.blockList().clear();
    }
}
