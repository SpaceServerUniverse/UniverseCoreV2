package space.yurisi.universecorev2.subplugins.autocollect.event;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.autocollect.command.AutoCollectCommand;
import space.yurisi.universecorev2.utils.Message;

import java.util.*;

public class Listener implements org.bukkit.event.Listener {

    private Map<String, Boolean> isCollect;
    private static Listener instance;

    public Listener(){
        this.isCollect = new HashMap<>();
        instance = this;
    }

    public static Listener getInstance(){
        return instance;
    }

    public void toggleCollect(Player player){
        this.isCollect.put(player.getUniqueId().toString(),!this.isCollect.get(player.getUniqueId().toString()));
    }

    public boolean isCollect(Player player){
        return this.isCollect.get(player.getUniqueId().toString());
    }

    @EventHandler
    public void onDrop(BlockDropItemEvent event) {
        Player player = event.getPlayer();
        if(!isCollect.get(player.getUniqueId().toString())) return;
        List<Item> itemEntity = event.getItems();
        outside:for(int i = 0; i < itemEntity.size(); i++){
            ItemStack dropItem = itemEntity.get(i).getItemStack();
            for (int j = 0; j < player.getInventory().getStorageContents().length; j++) {
                ItemStack item = player.getInventory().getItem(j);
                if(item == null){
                    player.getInventory().addItem(dropItem);
                    itemEntity.remove(i);
                    continue outside;
                }
                if(Objects.equals(item.getType(), dropItem.getType())){
                    int dropAmount = dropItem.getAmount();
                    int maxStackSize = item.getMaxStackSize();
                    int currentItemAmount = item.getAmount();
                    if(!Objects.equals(item.getItemMeta(), dropItem.getItemMeta())) continue;
                    if(maxStackSize - currentItemAmount < dropAmount) continue;
                    player.getInventory().addItem(dropItem);
                    itemEntity.remove(i);
                    continue outside;
                }
            }
        }
        boolean isDrop = false;
        for (Item dropItem : itemEntity) {
            if (dropItem == null) continue;
            isDrop = true;
            ItemStack item = dropItem.getItemStack();
            player.getWorld().dropItem(player.getLocation(), item);
        }
        if(isDrop){
            Message.sendWarningMessage(player, AutoCollectCommand.ACCommand, "インベントリが満杯です！");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        isCollect.put(event.getPlayer().getUniqueId().toString(), false);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        isCollect.remove(event.getPlayer().getUniqueId().toString());
    }
}
