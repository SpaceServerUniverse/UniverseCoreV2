package space.yurisi.universecorev2.subplugins.universeguns.event;

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
import org.bukkit.util.Vector;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;
import space.yurisi.universecorev2.subplugins.universeguns.item.ItemRegister;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GunEvent implements Listener {

    ArrayList<Player> isCooldown = new ArrayList<>();
    ArrayList<Player> isZoom = new ArrayList<>();
    private final HashMap<Player, BukkitRunnable> reloadingTasks = new HashMap<>();
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

            if (reloadingTasks.containsKey(player)) {
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

                        if(!gun.getType().equals("SR")) {
                            gun.shoot();
                            GunShot gunShot = new GunShot(player, gun, isZoom);
                            projectileData.put(gunShot.getProjectile(), gun);
                            for (int i = 0; i < gun.getBurst(); i++) {
                                new BukkitRunnable(){
                                    @Override
                                    public void run() {
                                        gun.shoot();
                                        GunShot burstShot = new GunShot(player, gun, isZoom);
                                        projectileData.put(burstShot.getProjectile(), gun);
                                    }
                                }.runTaskLater(plugin, 1L);

                            }

                        } else {
                            if(!isZoom.contains(player)){
                                Message.sendWarningMessage(player, "[武器AI]", "狙撃時のみ発射できます。");
                                return;
                            }

                            gun.shoot();
                            new SniperShot(player, gun);
                            if(Objects.equals(gun.getName(), "L96")){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_COPPER_DOOR_OPEN, 1.0F, 0.6F);
                                    }
                                }.runTaskLater(plugin, 5L);
                            }
                        }

                        if (gun.getCurrentAmmo() > 0) {
                            isCooldown.add(player);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    isCooldown.remove(player);
                                    if(Objects.equals(gun.getName(), "L96")){
                                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_COPPER_DOOR_CLOSE, 1.0F, 0.6F);
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
                        taskCancel(shootingTasks, player);
                    }
                    isShooting.put(player, false);
                }
            }.runTaskLater(plugin, 4); // 4tick


        } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {

            event.setCancelled(true);

            if(reloadingTasks.containsKey(player)){
                return;
            }
            if (isZoom.contains(player)) {
                player.setWalkSpeed(gun.getWeight());
                isZoom.remove(player);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PIG_SADDLE, 1.0F, 2.0F);
                gun.updateActionBar(player, false);
            } else {
                player.setWalkSpeed(gun.getIsZoomWalkSpeed());
                isZoom.add(player);
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE, 1.0F, 0.8F);
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
            if(event.getEntity().isDead()){
                return;
            }
            GunItem gun = projectileData.get(snowball);
            double damage = gun.getBaseDamage();
            Location loc = snowball.getLocation();
            LivingEntity entity = (LivingEntity) event.getEntity();

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
            if (isHeadShot(loc.getY(), entity)) {
                damage *= headShotTimes;
            }

            event.setDamage(damage);
            entity.setMaximumNoDamageTicks(0);
            entity.setNoDamageTicks(0);
            entity.setLastDamage(Integer.MAX_VALUE);

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

    private void taskCancel(HashMap<Player, BukkitRunnable> tasks, Player player){
        BukkitRunnable task = tasks.get(player);
        if(task != null){
            task.cancel();
        }
        tasks.remove(player);
    }

    private void removePlayer(Player player){
        if(isZoom.contains(player)){
            isZoom.remove(player);
            player.setWalkSpeed(0.2f);
        }
        if(isCooldown.contains(player)){
            isCooldown.remove(player);
        }
        if(reloadingTasks.containsKey(player)){
            taskCancel(reloadingTasks, player);
        }
        if(isShooting.containsKey(player)){
            isShooting.remove(player);
        }
        if(shootingTasks.containsKey(player)){
            taskCancel(shootingTasks, player);
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        ItemStack oldInHand = player.getInventory().getItem(event.getPreviousSlot());
        if(oldInHand != null && oldInHand.hasItemMeta()){
            String oldHandItemID = getGunID(oldInHand);
            if(oldHandItemID != null && ItemRegister.isGun(oldHandItemID)){
                GunItem oldGun = ItemRegister.getItem(oldHandItemID);
                if(reloadingTasks.containsKey(player)){
                    taskCancel(reloadingTasks, player);
                    oldGun.cancelReload();
                    oldGun.updateActionBar(player, false);
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
                GunItem newGun = ItemRegister.getItem(newHandItemID);
                player.setWalkSpeed(newGun.getWeight());
                newGun.updateActionBar(player, false);
            }
        }
        if(isZoom.contains(player)){
            isZoom.remove(player);
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
        if (gun.getIsReloading()) {
            return;
        }
        gun.startReload();
        if (isZoom.contains(player)) {
            player.setWalkSpeed(gun.getWeight());
            isZoom.remove(player);
        }
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.0F);
        BukkitRunnable reloadingTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || !reloadingTasks.containsKey(player)) {
                    gun.cancelReload();
                    cancel();
                    reloadingTasks.remove(player);
                    return;
                }
                gun.finishReload();
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0F, 1.0F);
                reloadingTasks.remove(player);
                gun.updateActionBar(player, isZoom.contains(player));
                }
            };

        reloadingTask.runTaskLater(plugin, gun.getReloadTime() / 50);
        reloadingTasks.put(player, reloadingTask);


        new BukkitRunnable() {
            @Override
            public void run() {
                if (!gun.getIsReloading() || !reloadingTasks.containsKey(player)) {
                    cancel();
                    gun.cancelReload();
                    return;
                }
                gun.updateActionBar(player, isZoom.contains(player));
            }
        }.runTaskTimer(UniverseCoreV2.getInstance(), 0, 2);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
//            if (isReloading.contains(player)) {
//                event.setCancelled(true);
//            }
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
        if (!reloadingTasks.containsKey(player)) {
            startReload(player, gun);
        }
    }
}

