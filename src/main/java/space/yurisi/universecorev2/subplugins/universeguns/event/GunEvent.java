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
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
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

    public GunEvent(Plugin plugin) {
        GunEvent.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        // 歩行速度をデフォルトに戻す
        Action action = event.getAction();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!itemInHand.hasItemMeta()) {
            return;
        }
        String handItemID = getGunID(itemInHand);
        if(handItemID == null || !ItemRegister.isGun(handItemID)){
            return;
        }

        GunItem gun = ItemRegister.getItem(handItemID);

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {

            event.setCancelled(true);
            if(isCooldown.contains(player)){
                return;
            }
            if ((gun.getType().equals("SR") || gun.getType().equals("EX")) && !isZoom.contains(player)) {
                Message.sendWarningMessage(player, "[武器AI]", "スコープを覗いてください。");
                return;
            }

            // 発射
            ShotEvent shotEvent = new ShotEvent(player, gun, isZoom);
            projectileData.put(shotEvent.getProjectile(), gun);

            // クールダウン
            int tick = gun.getFireRate();
            if (tick != 0) {
                isCooldown.add(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {isCooldown.remove(player);}
                }.runTaskLater(plugin, tick);
            }


        } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {

            event.setCancelled(true);
            if (isZoom.contains(player)) {
                player.setWalkSpeed(gun.getWeight());
                isZoom.remove(player);
            } else {
                player.setWalkSpeed(gun.getIsZoomWalkSpeed());
                isZoom.add(player);
            }

        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball snowball) {
            if (!projectileData.containsKey(snowball)) {
                return;
            }
            GunItem gun = projectileData.get(snowball);
            double damage = gun.getBaseDamage();

            if(gun.getIsExplosive()){
                float radius = gun.getExplosionRadius();
                snowball.getWorld().createExplosion(snowball.getLocation(), radius, false, false, snowball);
            }

            double neckHeight = 1.35;
            double headShotTimes = 1.5;
            if(snowball.getLocation().getY() > neckHeight){
                damage *= headShotTimes;
            }

            event.setDamage(damage);
            projectileData.remove(snowball);
            // TODO: ヒットエフェクト
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newInHand = player.getInventory().getItem(event.getNewSlot());
        if(newInHand == null){
            player.setWalkSpeed(0.2f);
        }else{
            if(!newInHand.hasItemMeta()){
                return;
            }
            String newHandItemID = getGunID(newInHand);
            if(newHandItemID == null){
                return;
            }
            if(ItemRegister.isGun(newHandItemID)){
                GunItem gun = ItemRegister.getItem(newHandItemID);
                player.setWalkSpeed(gun.getWeight());
            }
        }
        if(isZoom.contains(player)){
            isZoom.remove(player);
        }

    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        ItemStack mainHandItem = event.getMainHandItem();
        if (mainHandItem != null && mainHandItem.hasItemMeta()) {
            String handItemID = getGunID(mainHandItem);

            if (handItemID != null && ItemRegister.isGun(handItemID)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("オフハンドに武器を持つことはできません。");
            }
        }
    }

    private String getGunID(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return null;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(new NamespacedKey(plugin, UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING);
    }
}

