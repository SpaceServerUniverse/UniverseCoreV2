package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;
import space.yurisi.universecorev2.subplugins.universeguns.item.ItemRegister;
import space.yurisi.universecorev2.utils.Message;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.HashMap;

public class GunEvent implements Listener {

    ArrayList<Player> isCooldown = new ArrayList<>();
    ArrayList<Player> isZoom = new ArrayList<>();
    ArrayList<Player> isReloading = new ArrayList<>();
    public final HashMap<Entity, GunItem> projectileData = new HashMap<>();
    private static final ThreadLocal<Boolean> isHandlingExplosion = ThreadLocal.withInitial(() -> false);


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

            // 発射
            if (gun.getCurrentAmmo() == 0) {
                startReload(player, gun);
                return;
            }

            if ((gun.getType().equals("SR") || gun.getType().equals("EX")) && !isZoom.contains(player)) {
                Message.sendWarningMessage(player, "[武器AI]", "スコープを覗いてください。");
                return;
            }

            gun.shoot();

            if(!gun.getType().equals("SR")) {
                GunShot gunShot = new GunShot(player, gun, isZoom);
                projectileData.put(gunShot.getProjectile(), gun);
            } else {
                SniperShot sniperShot = new SniperShot(player, gun);
                RayTraceResult result = sniperShot.detectEntities(player);
                if(result == null){
                    return;
                }
                Entity entity = result.getHitEntity();
                if (entity instanceof LivingEntity livingEntity) {
                    double height = result.getHitPosition().getY();
                    double damage = gun.getBaseDamage();
                    if (isHeadShot(height, entity)) {
                        damage *= 1.5D;
                    }
                    livingEntity.damage(damage, player);
                }

            }

            // クールダウン
            int tick = gun.getFireRate();
            if (tick != 0 && gun.getCurrentAmmo() > 0) {
                isCooldown.add(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {isCooldown.remove(player);}
                }.runTaskLater(plugin, tick);
            }


        } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {

            event.setCancelled(true);

            if(isReloading.contains(player)){
                if(gun.getCurrentAmmo() == gun.getMagazineSize()){
                    isReloading.remove(player);
                }
                return;
            }
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
        if (isHandlingExplosion.get()) {
            return;
        }
        if (event.getDamager() instanceof Snowball snowball) {
            if (!projectileData.containsKey(snowball)) {
                return;
            }
            GunItem gun = projectileData.get(snowball);
            double damage = gun.getBaseDamage();
            Location loc = snowball.getLocation();

            if(gun.getIsExplosive()){
                float radius = gun.getExplosionRadius();
                isHandlingExplosion.set(true);
                try {
                    snowball.getWorld().createExplosion(loc, radius, false, false, snowball);
                } finally {
                    isHandlingExplosion.set(false);
                }
            }

            double headShotTimes = 1.5;
            if (isHeadShot(loc.getY(), event.getEntity())) {
                damage *= headShotTimes;
            }

            event.setDamage(damage);
            projectileData.remove(snowball);
            // TODO: ヒットエフェクト
        }
    }

    private boolean isHeadShot(double height, Entity entity){
        double neckHeight = 1.5;
        return height > entity.getLocation().getY() + neckHeight;
    }

    @EventHandler
    public void onBlockHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball snowball) {
            if (!projectileData.containsKey(snowball)) {
                return;
            }
            GunItem gun = projectileData.get(snowball);
            if(!gun.getIsExplosive()){
                return;
            }
            float radius = gun.getExplosionRadius();
            Location loc = snowball.getLocation();
            isHandlingExplosion.set(true);
            try {
                snowball.getWorld().createExplosion(loc, radius, false, false, snowball);
            } finally {
                isHandlingExplosion.set(false);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(isZoom.contains(player)){
            isZoom.remove(player);
            player.setWalkSpeed(0.2f);
        }
        if(isCooldown.contains(player)){
            isCooldown.remove(player);
        }
        if(isReloading.contains(player)){
            isReloading.remove(player);
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack oldInHand = player.getInventory().getItem(event.getPreviousSlot());
        if(oldInHand != null && oldInHand.hasItemMeta()){
            String oldHandItemID = getGunID(oldInHand);
            if(oldHandItemID != null && ItemRegister.isGun(oldHandItemID)){
                GunItem gun = ItemRegister.getItem(oldHandItemID);
                if(gun.getIsReloading()){
                    event.setCancelled(true);
                    return;
                }
            }
        }
        ItemStack newInHand = player.getInventory().getItem(event.getNewSlot());
        if(newInHand == null){
            player.setWalkSpeed(0.2f);
        }else{
            if(!newInHand.hasItemMeta()){
                player.setWalkSpeed(0.2f);
                return;
            }
            String newHandItemID = getGunID(newInHand);
            if(newHandItemID == null){
                player.setWalkSpeed(0.2f);
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
        if(isReloading.contains(player)){
            isReloading.remove(player);
        }
        if(isCooldown.contains(player)){
            isCooldown.remove(player);
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        ItemStack offHandItem = event.getOffHandItem();
        if (offHandItem.hasItemMeta()) {
            String handItemID = getGunID(offHandItem);

            if (handItemID != null && ItemRegister.isGun(handItemID)) {
                event.setCancelled(true);
                Message.sendWarningMessage(event.getPlayer(), "[武器AI]", "オフハンドに武器を持つことはできません。");
            }
        }
    }

    @EventHandler
    public void onPlayerJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getY() >= event.getTo().getY()) {
            return;
        }
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!itemInHand.hasItemMeta()) {
            return;
        }

        String handItemID = getGunID(itemInHand);
        if (handItemID != null && ItemRegister.isGun(handItemID)) {
            GunItem gun = ItemRegister.getItem(handItemID);
            if (!gun.getIsJumpEnabled()) {
                Vector velocity = player.getVelocity();
                double weight = gun.getWeight();
                velocity.setX(velocity.getX() * weight);
                velocity.setZ(velocity.getZ() * weight);
                player.setVelocity(velocity);
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

    private void startReload(Player player, GunItem gun) {
        if(gun.getIsReloading()){
            return;
        }
        isReloading.add(player);
        if(gun.getCurrentAmmo() == gun.getMagazineSize()){
            return;
        }
        gun.startReload();
        if(isZoom.contains(player)){
            player.setWalkSpeed(gun.getWeight());
            isZoom.remove(player);
        }
        Message.sendWarningMessage(player, "[武器AI]", "リロード中...");
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.0F);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || !isReloading.contains(player)) {
                    Message.sendWarningMessage(player, "[武器AI]", "リロードがキャンセルされました。");
                    gun.cancelReload();
                    gun.getItem();
                    return;
                }
                gun.finishReload();
                isReloading.remove(player);
                player.getInventory().setItemInMainHand(gun.getItem());
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0F, 1.0F);
            }
        }.runTaskLater(UniverseCoreV2.getInstance(), gun.getReloadTime() / 50);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!gun.getIsReloading()) {
                    cancel();
                    return;
                }
                player.getInventory().setItemInMainHand(gun.getItem());
            }
        }.runTaskTimer(UniverseCoreV2.getInstance(), 0, 2);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (isReloading.contains(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if (!droppedItem.hasItemMeta()) {
            return;
        }
        String itemID = getGunID(droppedItem);
        if (itemID == null || !ItemRegister.isGun(itemID)) {
            return;
        }
        GunItem gun = ItemRegister.getItem(itemID);
        if(gun.getCurrentAmmo() == gun.getMagazineSize()){
            return;
        }
        event.setCancelled(true);
        if (!isReloading.contains(player)) {
            startReload(player, gun);
        }
    }
}

