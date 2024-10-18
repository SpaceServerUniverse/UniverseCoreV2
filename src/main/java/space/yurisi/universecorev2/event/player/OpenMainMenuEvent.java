package space.yurisi.universecorev2.event.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.book.MainMenuBook;

public class OpenMainMenuEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack targetItem = event.getItem();

        switch (targetItem.getType()) {
            case KNOWLEDGE_BOOK -> {
                ItemStack item = UniverseItem.getItem(MainMenuBook.id).getItem();
                if (targetItem.isSimilar(item)) {
                    Bukkit.dispatchCommand(player, "menu");
                }
                event.setCancelled(true);
            }
        }

    }

}
