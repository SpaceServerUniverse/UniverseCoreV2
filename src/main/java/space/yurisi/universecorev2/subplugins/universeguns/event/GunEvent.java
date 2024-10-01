package space.yurisi.universecorev2.subplugins.universeguns.event;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
import java.util.Objects;

public class GunEvent implements Listener {

    ArrayList<Player> isCooldown = new ArrayList<>();
    ArrayList<Player> isZoom = new ArrayList<>();
    ArrayList<Player> isReloading = new ArrayList<>();
    private final HashMap<Player, BukkitRunnable> shootingTasks = new HashMap<>();
    private final HashMap<Player, Boolean> isShooting = new HashMap<>();
    public final HashMap<Entity, GunItem> projectileData = new HashMap<>();
    private static final ThreadLocal<Boolean> isHandlingExplosion = ThreadLocal.withInitial(() -> false);


    private static Plugin plugin;

    public GunEvent(Plugin plugin) {
        GunEvent.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
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
//            if(isCooldown.contains(player)){
//                return;
//            }
            if (isReloading.contains(player)) {
                return;
            }

            // 発射
            if (gun.getCurrentAmmo() == 0) {
                startReload(player, gun);
                return;
            }

            isShooting.put(player, true);
            if (!shootingTasks.containsKey(player)) {

                BukkitRunnable shootingTask = new BukkitRunnable() {
                    @Override
                    public void run() {

                        if (!isShooting.getOrDefault(player, false)) {
                            this.cancel();
                            shootingTasks.remove(player);
                            return;
                        }
                        gun.shoot();

                        if(!gun.getType().equals("SR")) {
                            GunShot gunShot = new GunShot(player, gun, isZoom);
                            projectileData.put(gunShot.getProjectile(), gun);
                        } else {
                            if(!isZoom.contains(player)){
                                Message.sendWarningMessage(player, "[武器AI]", "狙撃時のみ発射できます。");
                                return;
                            }
                            new SniperShot(player, gun);
                            if(Objects.equals(gun.getName(), "L96")){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_COPPER_DOOR_OPEN, 10.0F, 0.6F);
                                    }
                                }.runTaskLater(plugin, 5);
                            }
                        }

                        if (gun.getCurrentAmmo() > 0) {
                            isCooldown.add(player);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    isCooldown.remove(player);
                                    if(Objects.equals(gun.getName(), "L96")){
                                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_COPPER_DOOR_CLOSE, 10.0F, 0.6F);
                                    }
                                }
                            }.runTaskLater(plugin, gun.getFireRate());

                        }else{
                            isShooting.put(player, false);
                        }
                    }
                };

                shootingTask.runTaskTimer(plugin, 0, gun.getFireRate());
                shootingTasks.put(player, shootingTask);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!isShooting.getOrDefault(player, false)) {
                        BukkitRunnable task = shootingTasks.get(player);
                        if (task != null) {
                            task.cancel();
                            shootingTasks.remove(player);
                        }
                    }
                    isShooting.put(player, false);
                }
            }.runTaskLater(plugin, 4); // 4tick


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
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PIG_SADDLE, 5.0F, 2.0F);
                gun.updateActionBar(player, false);
            } else {
                player.setWalkSpeed(gun.getIsZoomWalkSpeed());
                isZoom.add(player);
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE, 5.0F, 0.8F);
                gun.updateActionBar(player, true);
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

    public boolean isHeadShot(double height, Entity entity){
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
        removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        removePlayer(event.getPlayer());
    }


    private void removePlayer(Player player){
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
        if(isShooting.containsKey(player)){
            isShooting.remove(player);
        }
        if(shootingTasks.containsKey(player)){
            BukkitRunnable task = shootingTasks.get(player);
            if(task != null){
                task.cancel();
            }
            shootingTasks.remove(player);
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
                gun.updateActionBar(player, false);
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
        gun.startReload();
        if(isZoom.contains(player)){
            player.setWalkSpeed(gun.getWeight());
            isZoom.remove(player);
        }
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.0F);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || !isReloading.contains(player)) {
                    gun.cancelReload();
                    cancel();
                    return;
                }
                gun.finishReload();
                isReloading.remove(player);
                player.getInventory().setItemInMainHand(gun.getItem());
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0F, 1.0F);

                gun.updateActionBar(player, isZoom.contains(player));
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

                gun.updateActionBar(player, isZoom.contains(player));
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
            player.setWalkSpeed(0.2f);
            return;
        }
        event.setCancelled(true);
        if (!isReloading.contains(player)) {
            startReload(player, gun);
        }
    }
}

