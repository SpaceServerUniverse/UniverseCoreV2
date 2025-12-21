package space.yurisi.universecorev2.subplugins.autocollect.event;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.autocollect.command.AutoCollectCommand;
import space.yurisi.universecorev2.subplugins.autocollect.data.AutoCollectMap;
import space.yurisi.universecorev2.utils.Message;

import java.util.List;
import java.util.Objects;

public class AutoCollectListener implements Listener{

    @EventHandler
    public void onDrop(BlockDropItemEvent event) {
        Player player = event.getPlayer();
        if(!AutoCollectMap.getInstance().isAutoCollect(player)) return;
        event.setCancelled(true);
        List<Item> itemEntity = event.getItems();
        int size = itemEntity.size();
        outside:for(int i = 0; i < size; i++){
            ItemStack dropItem = itemEntity.getFirst().getItemStack();
            player.sendMessage(dropItem.getType().name() + ", i: " + i + ", size: " + size);
            for (int j = 0; j < player.getInventory().getStorageContents().length; j++) {
                ItemStack item = player.getInventory().getItem(j);
                if(item == null){
                    player.getInventory().addItem(dropItem);
                    itemEntity.removeFirst();
                    continue outside;
                }
                if(Objects.equals(item.getType(), dropItem.getType())){
                    int dropAmount = dropItem.getAmount();
                    int maxStackSize = item.getMaxStackSize();
                    int currentItemAmount = item.getAmount();
                    if(!Objects.equals(item.getItemMeta(), dropItem.getItemMeta())) continue;
                    if(maxStackSize - currentItemAmount < dropAmount) continue;
                    player.getInventory().addItem(dropItem);
                    itemEntity.removeFirst();
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
            player.sendActionBar(Component.text("§b" + AutoCollectCommand.ACCommand + " §6インベントリが満杯です！"));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(AutoCollectMap.getInstance().isRegistered(event.getPlayer())) return;
        AutoCollectMap.getInstance().register(event.getPlayer());
    }
}
