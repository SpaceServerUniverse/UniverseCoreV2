package space.yurisi.universecorev2.subplugins.containerprotect.event.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;
import space.yurisi.universecorev2.subplugins.containerprotect.manager.ContainerProtectManager;

public class MoveItemEvent implements Listener {

    @EventHandler
    public void onMoveItem(InventoryMoveItemEvent event) {
        if (!(event.getSource().getHolder() instanceof InventoryHolder holder)) return;

        ContainerProtectManager containerProtectManager = ContainerProtectManager.getInstance();

        if (!containerProtectManager.isContainerProtect(holder.getInventory().getLocation())) return;

        event.setCancelled(true);
    }
}
