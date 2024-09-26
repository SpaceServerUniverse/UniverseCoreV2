package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;
import space.yurisi.universecorev2.subplugins.universeguns.item.ItemRegister;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class GunEvent implements Listener {

    ArrayList<Player> isCooldown = new ArrayList<>();
    ArrayList<Player> isZoom = new ArrayList<>();
    public final HashMap<Entity, GunItem> projectileData = new HashMap<>();

    private static Plugin plugin;

    public GunEvent(Plugin plugin){
        this.plugin = plugin;
    }

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
            String handItemID = container.get(new NamespacedKey(plugin, UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING);
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
                // 発射
                ShotEvent shotEvent = new ShotEvent(player, gun);
                projectileData.put(shotEvent.getProjectile(), gun);

                // クールダウン
                int tick = gun.getFireRate();
                if(tick != 0){
                    isCooldown.add(player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            isCooldown.remove(player);
                        }
                    }.runTaskLater(plugin, tick);
                }
            }
        } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
            // ズーム
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (!itemInHand.hasItemMeta()) {
                return;
            }
            ItemMeta meta = itemInHand.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String handItemID = container.get(new NamespacedKey(plugin, UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING);
            if (handItemID == null) {
                return;
            }
            // GunItemのIDのどれかに一致するか
            if (ItemRegister.isGun(handItemID)) {
                GunItem gun = ItemRegister.getItem(handItemID);
                if(isZoom.contains(player)){
                    player.setWalkSpeed(gun.getWeight());
                    isZoom.remove(player);
                }else{
                    player.setWalkSpeed(gun.getIsZoomWalkSpeed());
                    isZoom.add(player);
                }

            }
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Snowball snowball) {
            if (projectileData.containsKey(snowball)) {
                GunItem gun = projectileData.get(snowball);
                // TODO: 爆発系の分岐
                // TODO: ヘッドショット処理
                event.setDamage(gun.getBaseDamage());
                projectileData.remove(snowball);
                // TODO: ヒットエフェクト
            }
        }
    }
}
