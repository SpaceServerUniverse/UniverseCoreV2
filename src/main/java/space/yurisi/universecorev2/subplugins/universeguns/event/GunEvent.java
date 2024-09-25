package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;
import space.yurisi.universecorev2.subplugins.universeguns.item.ItemRegister;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;

public class GunEvent implements Listener {

    ArrayList<Player> isCooldown = new ArrayList<>();
    ArrayList<Player> isZoom = new ArrayList<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (!itemInHand.hasItemMeta()) {
                return;
            }
            ItemMeta meta = itemInHand.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String handItemID = container.get(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING);
            if (handItemID == null) {
                return;
            }
            // GunItemのIDのどれかに一致するか
            if (ItemRegister.isGun(handItemID) && !isCooldown.contains(player)) {

                GunItem gun = ItemRegister.getItem(handItemID);
                if((gun.getType().equals("SR") || gun.getType().equals("EX")) && !isZoom.contains(player)){
                    Message.sendWarningMessage(player, "[武器AI]", "スコープを覗いてください。");
                    return;
                }
                int tick = gun.getFireRate();
                isCooldown.add(player);
                if(tick != 0){
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            isCooldown.remove(player);
                        }
                    }.runTaskLater(UniverseCoreV2.getInstance(), tick);
                }
            }
        }
    }
}
