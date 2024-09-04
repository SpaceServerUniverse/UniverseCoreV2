package space.yurisi.universecorev2.subplugins.mywarp.menu;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class AddMywarpInventoryMenu implements Listener {

    private static final Component MENU_NAME = Component.text("Mywarp作成");
    private static final String MENU_TAG = "mywarp_add";

    private static final Component FIRST_ITEM_NAME = Component.text("マイワープ名を入力して下さい。");

    public Inventory createMenu(){
        Inventory inventory = Bukkit.createInventory(null, InventoryType.ANVIL, MENU_NAME);
        if(inventory instanceof AnvilInventory){
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(FIRST_ITEM_NAME);
            item.setItemMeta(meta);
            ((AnvilInventory) inventory).setFirstItem(item);
        }
        return inventory;
    }

    public void sendMenu(Player player){
        player.addScoreboardTag(MENU_TAG);
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1 ,0.5F);
        player.openInventory(createMenu());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(player.getScoreboardTags().contains(MENU_TAG)){
            event.setCancelled(true);
            if(!event.getAction().equals(InventoryAction.PICKUP_ALL)) return;
            if(player.getGameMode() == GameMode.SPECTATOR) return;
            ItemStack item = event.getCurrentItem();
            if(item == null) return;
            item = item.clone();
            ItemMeta meta = item.getItemMeta();
            if(meta.displayName() != FIRST_ITEM_NAME){
                player.sendMessage(meta.displayName());
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(player.getScoreboardTags().contains(MENU_TAG)) player.removeScoreboardTag(MENU_TAG);
    }
}
