package space.yurisi.universecorev2.subplugins.mywarp.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;

import java.util.Objects;

public class MywarpInventoryMenu implements Listener {

    private static final Component MENU_NAME = Component.text("Mywarp");
    private static final String MENU_TAG = "mywarp_top";

    public Inventory createMenu(){
        int row = 1;
        Inventory inventory = Bukkit.createInventory(null, row * 9, MENU_NAME);

        inventory.addItem(getMenuItem(Material.SPECTRAL_ARROW, "追加", "追加"));
        inventory.addItem(getMenuItem(Material.MAP, "ワープ", "ワープ"));

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
            if(isMenuItem(item)){
                ItemMeta meta = item.getItemMeta();
                if(meta == null) return;
                PersistentDataContainer container = meta.getPersistentDataContainer();
                String name = container.get(new NamespacedKey(MENU_TAG, MENU_TAG), PersistentDataType.STRING);
                player.closeInventory();
                if(Objects.equals(name, "追加")) {
                    AddMywarpInventoryMenu addmenu = new AddMywarpInventoryMenu();
                    addmenu.sendMenu(player);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(player.getScoreboardTags().contains(MENU_TAG)) player.removeScoreboardTag(MENU_TAG);
    }

    private ItemStack getMenuItem(Material base_item, String name, String tag){
        ItemStack item = new ItemStack(base_item);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name));
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(MENU_TAG, MENU_TAG), PersistentDataType.STRING, tag);
        item.setItemMeta(meta);
        return item;
    }

    private Boolean isMenuItem(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return false;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(new NamespacedKey(MENU_TAG, MENU_TAG), PersistentDataType.STRING);
    }
}
