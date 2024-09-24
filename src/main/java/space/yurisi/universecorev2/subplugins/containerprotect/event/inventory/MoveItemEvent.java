package space.yurisi.universecorev2.subplugins.containerprotect.event.inventory;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;
import space.yurisi.universecorev2.subplugins.containerprotect.event.api.ContainerProtectAPI;

public class MoveItemEvent implements Listener {

    @EventHandler
    public void onMoveItem(InventoryMoveItemEvent event) {
        if (!(event.getSource().getHolder() instanceof InventoryHolder holder)) return;

        ContainerProtectAPI containerProtectManager = ContainerProtectAPI.getInstance();

        Location location = holder.getInventory().getLocation();
        if(location == null) return;
        if (!containerProtectManager.isContainerProtect(location)) return;

        event.setCancelled(true);
    }
}
