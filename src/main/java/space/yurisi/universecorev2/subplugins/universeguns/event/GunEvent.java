package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.Particle;
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
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;
import space.yurisi.universecorev2.subplugins.universeguns.core.GunStatus;
import space.yurisi.universecorev2.subplugins.universeguns.manager.GunStatusManager;
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
    public final HashMap<Entity, Gun> projectileData = new HashMap<>();
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
        ItemMeta meta = itemInHand.getItemMeta();
        if (!itemInHand.hasItemMeta()) {
            return;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();

        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        NamespacedKey gunSerialKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_SERIAL);

        if(!Gun.isGun(itemInHand)){
            return;
        }

        String handItemID = container.get(itemKey, PersistentDataType.STRING);
        String gunSerial = container.get(gunSerialKey, PersistentDataType.STRING);

        CustomItem gunItem = UniverseItem.getItem(handItemID);

        if(!(gunItem instanceof Gun gun)){
            return;
        }

        if(!GunStatusManager.isExists(gunSerial)){
            GunStatusManager.register(gunSerial, gun);
        }

        GunStatus gunStatus = GunStatusManager.get(gunSerial);

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            event.setCancelled(true);

            if (reloadingTasks.containsKey(player)) {
                return;
            }

            // 発射
            if (gunStatus.getCurrentAmmo() == 0) {
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

                        if(!gun.getType().equals(GunType.SR)) {

                            gunStatus.shoot();
                                GunShot gunShot = new GunShot(player, gun, isZoom);
                                projectileData.put(gunShot.getProjectile(), gun);
                                if (gun.getBurst() != 0) {
                                    for (int i = 0; i < gun.getBurst(); i++) {
                                        if (gunStatus.getCurrentAmmo() == 0) {
                                            break;
                                        }
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                gunStatus.shoot();
                                                GunShot burstShot = new GunShot(player, gun, isZoom);
                                                projectileData.put(burstShot.getProjectile(), gun);
                                            }
                                        }.runTaskLater(plugin, 1L);

                                    }
                                }

                            } else{
                                if (!isZoom.contains(player)) {
                                    Message.sendWarningMessage(player, "[武器AI]", "狙撃時のみ発射できます。");
                                    return;
                                }

                            gunStatus.shoot();
                                new SniperShot(player, gun);
                                if (Objects.equals(gun.getName(), "L96A1")) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_COPPER_DOOR_OPEN, 1.0F, 0.6F);
                                        }
                                    }.runTaskLater(plugin, 5L);
                                }
                            }

                            if (gunStatus.getCurrentAmmo() > 0) {
                                isCooldown.add(player);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        isCooldown.remove(player);
                                        if (Objects.equals(gun.getName(), "L96A1")) {
                                            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_COPPER_DOOR_CLOSE, 1.0F, 0.6F);
                                        }
                                    }
                                }.runTaskLater(plugin, gun.getFireRate());

                            } else {
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
                gunStatus.updateActionBar(player, false);
            } else {
                player.setWalkSpeed(gun.getIsZoomWalkSpeed());
                isZoom.add(player);
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE, 1.0F, 0.8F);
                gunStatus.updateActionBar(player, true);
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
            Gun gun = projectileData.get(snowball);
            double damage = gun.getBaseDamage();
            Location loc = snowball.getLocation();
            LivingEntity entity = (LivingEntity) event.getEntity();

            if(gun.getIsExplosive()){
                float radius = gun.getExplosionRadius();
                isHandlingExplosion.set(true);
                try {
                    snowball.getWorld().createExplosion(loc, radius, false, false, snowball);
                    snowball.getWorld().spawnParticle(Particle.EXPLOSION, loc, 1);
                } finally {
                    isHandlingExplosion.set(false);
                }
            }

            double headShotTimes = 1.5;
            if (isHeadShot(loc.getY(), entity)) {
                damage *= headShotTimes;
            }

//            event.setDamage(damage);
            double newHealth = entity.getHealth() - damage;
            if (newHealth <= 0) {
                newHealth = 0;
            }
            entity.setHealth(newHealth);
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
            Gun gun = projectileData.get(snowball);
            if(!gun.getIsExplosive()){
                return;
            }
            float radius = gun.getExplosionRadius();
            Location loc = snowball.getLocation();
            isHandlingExplosion.set(true);
            try {
                snowball.getWorld().createExplosion(loc, radius, false, false, snowball);

                snowball.getWorld().spawnParticle(Particle.EXPLOSION, loc, 1);
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
            ItemMeta oldMeta = oldInHand.getItemMeta();
            PersistentDataContainer oldContainer = oldMeta.getPersistentDataContainer();
            NamespacedKey oldItemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
            NamespacedKey gunSerialKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_SERIAL);
            if(!Gun.isGun(oldInHand)){
                return;
            }

            String oldHandItemID = oldContainer.get(oldItemKey, PersistentDataType.STRING);
            String gunSerial = oldContainer.get(gunSerialKey, PersistentDataType.STRING);
            CustomItem oldGunItem = UniverseItem.getItem(oldHandItemID);

            if(!(oldGunItem instanceof Gun oldGun)){
                return;
            }

            if(!GunStatusManager.isExists(gunSerial)){
                GunStatusManager.register(gunSerial, oldGun);
            }

            GunStatus gunStatus = GunStatusManager.get(gunSerial);

            if(reloadingTasks.containsKey(player)){
                taskCancel(reloadingTasks, player);
                gunStatus.cancelReload();
                gunStatus.updateActionBar(player, false);
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
            ItemMeta newMeta = newInHand.getItemMeta();
            PersistentDataContainer newContainer = newMeta.getPersistentDataContainer();
            NamespacedKey newItemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
            NamespacedKey newGunKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN);
            if(!newContainer.has(newItemKey, PersistentDataType.STRING) || !newContainer.has(newGunKey, PersistentDataType.BOOLEAN)){
                player.setWalkSpeed(0.2f);
                return;
            }
            String newHandItemID = newContainer.get(newItemKey, PersistentDataType.STRING);
            CustomItem newGunItem = UniverseItem.getItem(newHandItemID);
            if(!(newGunItem instanceof Gun newGun)){
                player.setWalkSpeed(0.2f);
                return;
            }

            player.setWalkSpeed(newGun.getWeight());
            newGun.updateActionBar(player, false);

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
            ItemMeta meta = offHandItem.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
            NamespacedKey gunKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN);

            if(!container.has(itemKey, PersistentDataType.STRING) || !container.has(gunKey, PersistentDataType.BOOLEAN)){
                return;
            }
            String handItemID = container.get(itemKey, PersistentDataType.STRING);
            CustomItem gunItem = UniverseItem.getItem(handItemID);
            if(!(gunItem instanceof Gun)){
                return;
            }

            event.setCancelled(true);
            Message.sendWarningMessage(event.getPlayer(), "[武器AI]", "オフハンドに武器を持つことはできません。");

        }
    }

    @EventHandler
    public void onPlayerJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getY() >= event.getTo().getY()) {
            return;
        }
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta meta = itemInHand.getItemMeta();
        if (!itemInHand.hasItemMeta()) {
            return;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();

        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        NamespacedKey gunKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN);

        if(!container.has(itemKey, PersistentDataType.STRING) || !container.has(gunKey, PersistentDataType.BOOLEAN)){
            return;
        }

        String handItemID = container.get(itemKey, PersistentDataType.STRING);
        CustomItem gunItem = UniverseItem.getItem(handItemID);

        if(!(gunItem instanceof Gun gun)){
            return;
        }

        if (!gun.getIsJumpEnabled()) {
            Vector velocity = player.getVelocity();
            double weight = gun.getWeight();
            velocity.setX(velocity.getX() * weight);
            velocity.setZ(velocity.getZ() * weight);
            player.setVelocity(velocity);
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

    private void startReload(Player player, Gun gun) {
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
        ItemMeta meta = droppedItem.getItemMeta();
        if (!droppedItem.hasItemMeta()) {
            return;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        NamespacedKey gunKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN);

        if(!container.has(itemKey, PersistentDataType.STRING) || !container.has(gunKey, PersistentDataType.BOOLEAN)){
            return;
        }

        String handItemID = container.get(itemKey, PersistentDataType.STRING);
        CustomItem gunItem = UniverseItem.getItem(handItemID);

        if(!(gunItem instanceof Gun gun)){
            return;
        }

        if(gunStatus.getCurrentAmmo() == gun.getMagazineSize()){
            player.setWalkSpeed(0.2f);
            return;
        }
        event.setCancelled(true);
        if (!reloadingTasks.containsKey(player)) {
            startReload(player, gun);
        }
    }
}

