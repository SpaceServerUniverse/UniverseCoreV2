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
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;
import space.yurisi.universecorev2.subplugins.universeguns.core.BulletData;
import space.yurisi.universecorev2.subplugins.universeguns.core.DamageCalculator;
import space.yurisi.universecorev2.subplugins.universeguns.core.GunStatus;
import space.yurisi.universecorev2.subplugins.universeguns.manager.GunStatusManager;
import space.yurisi.universecorev2.subplugins.universeguns.menu.AmmoManagerInventoryMenu;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GunEvent implements Listener {


    ArrayList<Player> isZoom = new ArrayList<>();
    private final HashMap<Player, BukkitRunnable> reloadingTasks = new HashMap<>();
    private final HashMap<String, BukkitRunnable> shootingTasks = new HashMap<>();
    private final HashMap<String, Boolean> isShooting = new HashMap<>();
    public final HashMap<Entity, BulletData> projectileData = new HashMap<>();
    private static final ThreadLocal<Boolean> isHandlingExplosion = ThreadLocal.withInitial(() -> false);


    private static Plugin plugin;

    private UniverseCoreAPIConnector connector;

    public GunEvent(Plugin plugin, UniverseCoreAPIConnector connector) {
        GunEvent.plugin = plugin;
        this.connector = connector;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        // オフハンドのイベントの場合は無視
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta meta = itemInHand.getItemMeta();
        if (!itemInHand.hasItemMeta()) {
            return;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();

        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        NamespacedKey gunSerialKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_SERIAL);

        if(container.has(itemKey, PersistentDataType.STRING) && Objects.equals(container.get(itemKey, PersistentDataType.STRING), "magazine_bag")){
            player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 1, 1);
            AmmoManagerInventoryMenu menu = new AmmoManagerInventoryMenu(connector);
            menu.sendMenu(player);
            return;
        }

        Gun gun = Gun.getGun(itemInHand);
        if(gun == null){
            return;
        }

        String gunSerial = container.get(gunSerialKey, PersistentDataType.STRING);

        if (!GunStatusManager.isExists(gunSerial)) {
            GunStatusManager.register(gunSerial, gun, connector);
        }

        GunStatus gunStatus = GunStatusManager.get(gunSerial);

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            event.setCancelled(true);

            if (reloadingTasks.containsKey(player)) {
                return;
            }

            // 発射
            if (gunStatus.getMagazineAmmo() == 0) {
                startReload(player, gun, gunStatus);
                return;
            }

            isShooting.put(gunSerial, true);
            if (!shootingTasks.containsKey(gunSerial)) {

                BukkitRunnable shootingTask = new BukkitRunnable() {
                    @Override
                    public void run() {

                        if (!isShooting.getOrDefault(gunSerial, false)) {
                            this.cancel();
                            shootingTasks.remove(gunSerial);
                            return;
                        }

                        if (gun.getType().equals(GunType.HG) || gun.getType().equals(GunType.AR) || gun.getType().equals(GunType.EX)
                                || gun.getType().equals(GunType.SMG) || gun.getType().equals(GunType.LMG)) {

                            gunStatus.shoot();
                            GunShot gunShot = new GunShot(player, gun, gunStatus, isZoom);
                            projectileData.put(gunShot.getProjectile(), new BulletData(gun, player, gunShot.getLaunchLocation()));

                            if (gun.getBurst() != 0) {
                                for (int i = 0; i < gun.getBurst(); i++) {
                                    if (gunStatus.getMagazineAmmo() == 0) {
                                        break;
                                    }

                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            gunStatus.shoot();
                                            GunShot burstShot = new GunShot(player, gun, gunStatus, isZoom);
                                            projectileData.put(burstShot.getProjectile(), new BulletData(gun, player, burstShot.getLaunchLocation()));
                                        }
                                    }.runTaskLater(plugin, 1L);

                                }
                            }

                        } else if (gun.getType().equals(GunType.SR)) {
                            if (!isZoom.contains(player)) {
                                Message.sendWarningMessage(player, "[武器AI]", "狙撃時のみ発射できます。");
                                return;
                            }

                            gunStatus.shoot();
                            new SniperShot(player, gun, gunStatus);
                            if (Objects.equals(gun.getName(), "L96A1")) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.playSound(player.getLocation(), Sound.BLOCK_COPPER_DOOR_OPEN, 1.0F, 0.6F);
                                    }
                                }.runTaskLater(plugin, 5L);
                            }

                        } else if (gun.getType().equals(GunType.SG)) {
                            gunStatus.shoot();
                            ShotgunShot shotgunShot = new ShotgunShot(player, gun, gunStatus, isZoom);
                            List<Snowball> projectiles = shotgunShot.getProjectiles();
                            for (Snowball projectile : projectiles) {
                                projectileData.put(projectile, new BulletData(gun, player, player.getEyeLocation()));
                            }
                        }

                        if (gunStatus.getMagazineAmmo() > 0) {

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (Objects.equals(gun.getName(), "L96A1")) {
                                        player.playSound(player.getLocation(), Sound.BLOCK_COPPER_DOOR_CLOSE, 1.0F, 0.6F);
                                    }
                                }
                            }.runTaskLater(plugin, gun.getFireRate() - 5);

                        } else {
                            isShooting.put(gunSerial, false);
                        }

                    }
                };

                shootingTask.runTaskTimer(plugin, 0, gun.getFireRate());
                shootingTasks.put(gunSerial, shootingTask);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!isShooting.getOrDefault(gunSerial, false)) {
                        shootingTaskCancel(shootingTasks, gunSerial);
                    }
                    isShooting.put(gunSerial, false);
                }
            }.runTaskLater(plugin, 4); // 4tick


        } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {

            event.setCancelled(true);

            if (reloadingTasks.containsKey(player)) {
                return;
            }
            if (isZoom.contains(player)) {
                player.setWalkSpeed(gun.getWeight());
                isZoom.remove(player);
                player.playSound(player.getLocation(), Sound.ENTITY_PIG_SADDLE, 1.0F, 2.0F);
                gunStatus.updateActionBar(player, false);
            } else {
                player.setWalkSpeed(gun.getIsZoomWalkSpeed());
                isZoom.add(player);
                player.playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE, 1.0F, 0.8F);
                gunStatus.updateActionBar(player, true);
            }

        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (isHandlingExplosion.get()) {
            return;
        }
        Entity entity = event.getEntity();
        if (event.getEntity().isDead()) {
            return;
        }
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        if (event.getDamager() instanceof Snowball snowball) {
            if (!projectileData.containsKey(snowball)) {
                return;
            }
            BulletData data = projectileData.get(snowball);
            Player shooter = data.getPlayer();
            Gun gun = data.getGun();
            double damage = gun.getBaseDamage();
            Location loc = snowball.getLocation();

            if (gun.getIsExplosive()) {
                float radius = gun.getExplosionRadius();
                isHandlingExplosion.set(true);
                try {
                    snowball.getWorld().createExplosion(loc, radius, false, false, snowball);
                    snowball.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, loc, 1);
                } finally {
                    isHandlingExplosion.set(false);
                }
            }


            double headShotTimes = 1.5;
            if (DamageCalculator.isHeadShot(loc.getY(), entity)) {
                damage *= headShotTimes;
                shooter.playSound(shooter.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0F, 1.0F);
            } else {
                shooter.playSound(shooter.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1.0F, 1.0F);
            }

            if(gun.getType().equals(GunType.SG)){
                damage = DamageCalculator.getSlopedDamage(data.getLocation(), livingEntity, gun.getRange(), damage);
            }


//            double newHealth = livingEntity.getHealth() - damage;
//            if (newHealth <= 0) {
//                newHealth = 0;
//            }
            livingEntity.setMaximumNoDamageTicks(0);
            livingEntity.setNoDamageTicks(0);
            livingEntity.setLastDamage(Integer.MAX_VALUE);
//            livingEntity.setHealth(newHealth);
            event.setDamage(damage);
            projectileData.remove(snowball);
        } else {
            livingEntity.setMaximumNoDamageTicks(10);
            livingEntity.setNoDamageTicks(10);
        }
    }

    @EventHandler
    public void onPlayerHitByBlock(EntityDamageByBlockEvent event) {
        if (isHandlingExplosion.get()) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity.isDead()) {
            return;
        }
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        livingEntity.setMaximumNoDamageTicks(10);
        livingEntity.setNoDamageTicks(10);
    }

    @EventHandler
    public void onBlockHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball snowball) {
            if (!projectileData.containsKey(snowball)) {
                return;
            }
            if(event.getHitBlock() == null){
                return;
            }
            BulletData data = projectileData.get(snowball);
            Location loc = snowball.getLocation();
            Gun gun = data.getGun();
            if (event.getHitBlock().getType().toString().contains("GLASS")) {
                snowball.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2.0F, 1.0F);
            }
            if (!gun.getIsExplosive()) {
                return;
            }
            float radius = gun.getExplosionRadius();
            isHandlingExplosion.set(true);
            try {
                snowball.getWorld().createExplosion(loc, radius, false, false, snowball);

                snowball.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, loc, 1);
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

    private void taskCancel(HashMap<Player, BukkitRunnable> tasks, Player player) {
        BukkitRunnable task = tasks.get(player);
        if (task != null) {
            task.cancel();
        }
        tasks.remove(player);
    }

    private void shootingTaskCancel(HashMap<String, BukkitRunnable> tasks, String gunSerial) {
        BukkitRunnable task = tasks.get(gunSerial);
        if (task != null) {
            task.cancel();
        }
        tasks.remove(gunSerial);
    }

    private void removePlayer(Player player) {
        if (isZoom.contains(player)) {
            isZoom.remove(player);
            player.setWalkSpeed(0.2f);
        }
        if (reloadingTasks.containsKey(player)) {
            taskCancel(reloadingTasks, player);
        }
//        if (isShooting.containsKey(gunSerial)) {
//            isShooting.remove(gunSerial);
//        }
//        if (shootingTasks.containsKey(player)) {
//            taskCancel(shootingTasks, gunStatus);
//        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        ItemStack oldInHand = player.getInventory().getItem(event.getPreviousSlot());
        if (oldInHand != null && oldInHand.hasItemMeta()) {
            cancelOldGun(oldInHand, player);
        }

        ItemStack newInHand = player.getInventory().getItem(event.getNewSlot());
        if (newInHand == null) {
            player.setWalkSpeed(0.2f);
        } else {
            if (!newInHand.hasItemMeta()) {
                player.setWalkSpeed(0.2f);
                return;
            }
            ItemMeta newMeta = newInHand.getItemMeta();
            PersistentDataContainer newContainer = newMeta.getPersistentDataContainer();
            NamespacedKey newItemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
            NamespacedKey gunSerialKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_SERIAL);

            Gun gun = Gun.getGun(newInHand);
            if (gun == null) {
                player.setWalkSpeed(0.2f);
                return;
            }

            if(!connector.isExistsAmmoData(player)){
                try {
                    connector.AmmoDataInit(player);
                } catch (UserNotFoundException e) {
                    Message.sendErrorMessage(player, "[武器AI]", "ユーザーが見つかりませんでした。");
                }
            }

            String newHandItemID = newContainer.get(newItemKey, PersistentDataType.STRING);
            String gunSerial = newContainer.get(gunSerialKey, PersistentDataType.STRING);
            CustomItem newGunItem = UniverseItem.getItem(newHandItemID);
            if (!(newGunItem instanceof Gun newGun)) {
                player.setWalkSpeed(0.2f);
                return;
            }


            if (!GunStatusManager.isExists(gunSerial)) {
                GunStatusManager.register(gunSerial, newGun, connector);
            }

            GunStatus gunStatus = GunStatusManager.get(gunSerial);

            try {
                gunStatus.updateAmmo(player, isZoom.contains(player));
            } catch (UserNotFoundException | AmmoNotFoundException e) {
                throw new RuntimeException(e);
            }

            player.setWalkSpeed(newGun.getWeight());
            gunStatus.updateActionBar(player, false);

        }
        if (isZoom.contains(player)) {
            isZoom.remove(player);
        }
    }

    private void cancelOldGun(ItemStack oldInHand, Player player) {
        ItemMeta oldMeta = oldInHand.getItemMeta();
        PersistentDataContainer oldContainer = oldMeta.getPersistentDataContainer();
        NamespacedKey gunSerialKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_SERIAL);
        Gun oldGun = Gun.getGun(oldInHand);
        if (oldGun == null) {
            return;
        }

        String oldGunSerial = oldContainer.get(gunSerialKey, PersistentDataType.STRING);

        if(!connector.isExistsAmmoData(player)){
            try {
                connector.AmmoDataInit(player);
            } catch (UserNotFoundException e) {
                Message.sendErrorMessage(player, "[武器AI]", "ユーザーが見つかりませんでした。");
            }
        }

        if (!GunStatusManager.isExists(oldGunSerial)) {
            GunStatusManager.register(oldGunSerial, oldGun, connector);
        }

        GunStatus oldGunStatus = GunStatusManager.get(oldGunSerial);
        try {
            oldGunStatus.updateAmmo(player, isZoom.contains(player));
        } catch (UserNotFoundException | AmmoNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (reloadingTasks.containsKey(player)) {
            taskCancel(reloadingTasks, player);
            oldGunStatus.cancelReload();
            oldGunStatus.updateActionBar(player, false);
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        ItemStack offHandItem = event.getOffHandItem();
        if (offHandItem.hasItemMeta()) {

            Gun gun = Gun.getGun(offHandItem);
            if (gun == null) {
                return;
            }

            event.setCancelled(true);
            Message.sendWarningMessage(event.getPlayer(), "[武器AI]", "オフハンドに武器を持つことはできません。");
        }
    }

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event){
        Player player = (Player) event.getView().getPlayer();
        CraftingInventory inventory = event.getInventory();
        ItemStack[] matrix = inventory.getMatrix();
        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        for (ItemStack itemStack : matrix) {
            if (itemStack == null) {
                continue;
            }
            if (!itemStack.hasItemMeta()) {
                continue;
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer itemContainer = itemMeta.getPersistentDataContainer();
            String itemID2 = itemContainer.get(itemKey, PersistentDataType.STRING);
            if (itemID2 == null) {
                continue;
            }
            CustomItem item2 = UniverseItem.getItem(itemID2);
            if (item2 instanceof Gun) {
                event.getInventory().setResult(null);
                Message.sendWarningMessage(player, "[武器AI]", "武器はクラフトできません。");
                return;
            }
            if(Objects.equals(itemID2, "magazine_bag")){
                event.getInventory().setResult(null);
                Message.sendWarningMessage(player, "[武器AI]", "マガジンバッグはクラフトできません。");
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(event.getFrom().getY() >= event.getTo().getY()){
            return;
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!itemInHand.hasItemMeta()) {
            return;
        }

        Gun gun = Gun.getGun(itemInHand);
        if (gun == null) {
            return;
        }

        if (!gun.getIsJumpEnabled()) {
            player.setVelocity(new Vector(0, 0, 0));
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

    private void startReload(Player player, Gun gun, GunStatus gunStatus) {
        if (gunStatus.getIsReloading()) {
            return;
        }
        try {
            int reloadTime = gun.getReloadTime();
            double reloadTimeCoefficient = 1.0;
            ItemStack leggings = player.getInventory().getLeggings();
            if (leggings != null && leggings.hasItemMeta()) {
                ItemMeta leggingsMeta = leggings.getItemMeta();
                PersistentDataContainer container = leggingsMeta.getPersistentDataContainer();
                NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
                if (container.has(itemKey, PersistentDataType.STRING) &&  Objects.equals(container.get(itemKey, PersistentDataType.STRING), "tactical_leggings")) {
                    reloadTimeCoefficient -= 0.2;
                }
            }
            ItemStack chestplate = player.getInventory().getChestplate();
            if (chestplate != null && chestplate.hasItemMeta()) {
                ItemMeta chestplateMeta = chestplate.getItemMeta();
                PersistentDataContainer container = chestplateMeta.getPersistentDataContainer();
                NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
                if (container.has(itemKey, PersistentDataType.STRING) &&  Objects.equals(container.get(itemKey, PersistentDataType.STRING), "tactical_vest")) {
                    reloadTimeCoefficient -= 0.3;
                }
            }

            double newReloadTime = reloadTime * reloadTimeCoefficient;
            reloadTime = (int) newReloadTime;

            boolean result = gunStatus.startReload(reloadTime, player);
            if (!result) {
                Message.sendWarningMessage(player, "[武器AI]", "弾薬を補充してください");
                return;
            }
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.0F);
            BukkitRunnable reloadingTask = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.isOnline() || !reloadingTasks.containsKey(player)) {
                        gunStatus.cancelReload();
                        cancel();
                        reloadingTasks.remove(player);
                        return;
                    }
                    try {
                        gunStatus.finishReload(player);
                    } catch (UserNotFoundException | AmmoNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0F, 1.0F);
                    reloadingTasks.remove(player);
                    gunStatus.updateActionBar(player, isZoom.contains(player));
                }
            };

            reloadingTask.runTaskLater(plugin, reloadTime / 50);
            reloadingTasks.put(player, reloadingTask);


            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!gunStatus.getIsReloading() || !reloadingTasks.containsKey(player)) {
                        cancel();
                        gunStatus.cancelReload();
                        return;
                    }
                    gunStatus.updateActionBar(player, isZoom.contains(player));
                }
            }.runTaskTimer(UniverseCoreV2.getInstance(), 0, 2);
        } catch (UserNotFoundException e){
            Message.sendErrorMessage(player, "[武器AI]", "ユーザーが見つかりませんでした。");
        } catch (AmmoNotFoundException e){
            Message.sendErrorMessage(player, "[武器AI]", "弾薬が見つかりませんでした。");
        }

    }

//    @EventHandler
//    public void onInventoryClick(InventoryClickEvent event) {
//        if (event.getWhoClicked() instanceof Player player) {
//            player.sendMessage("called");
//            int destinationSlot = event.getRawSlot();
//            int slot = event.getSlot();
//            ItemStack item = event.getCurrentItem();
//            if(item != null && item.hasItemMeta()){
//                Gun newGun = Gun.getGun(item);
//                if(newGun != null){
//                    player.getInventory().setItem(slot, null);
//                    return;
//                }
//            }
//
//            ItemStack oldItem = event.getCursor();
//            if (!oldItem.hasItemMeta()) {
//                return;
//            }
//            Gun gun = Gun.getGun(oldItem);
//            if(gun == null){
//                return;
//            }
//
//            if (reloadingTasks.containsKey(player)) {
//                event.setCancelled(true);
//                return;
//            }
//
//            if(destinationSlot == 45){
//                // オフハンド
//                Message.sendWarningMessage(player, "[武器AI]", "オフハンドに武器を持つことはできません。");
//                event.setCancelled(true);
//                putGunToEmptySlot(player, oldItem);
//                return;
//            }
//            if(destinationSlot <= 35) {
//                return;
//            }
//
//            boolean[] result = checkEquipmentLimit(player);
//            if(!result[0] && !result[1]){
//                event.setCancelled(true);
//                Message.sendWarningMessage(player, "[武器AI]", "これ以上武器を持つことはできません。");
//                putGunToEmptySlot(player, oldItem);
//
//                return;
//            }
//
//            if(gun.getEquipmentType() == GunType.PRIMARY && !result[0]){
//                event.setCancelled(true);
//                Message.sendWarningMessage(player, "[武器AI]", "プライマリの所持制限を超えています。");
//                putGunToEmptySlot(player, oldItem);
//            }else if(gun.getEquipmentType() == GunType.SECONDARY && !result[1]){
//                event.setCancelled(true);
//                Message.sendWarningMessage(player, "[武器AI]", "セカンダリの所持制限を超えています。");
//                putGunToEmptySlot(player, oldItem);
//            }
//            player.updateInventory();
//        }
//    }
//
//    private void putGunToEmptySlot(Player player, ItemStack item){
//        ItemStack[] contents = player.getInventory().getContents();
//        int emptySlot = -1;
//
//        for (int i = 9; i < 36; i++) {
//            if (contents[i] == null) {
//                emptySlot = i;
//                break;
//            }else{
//                player.sendMessage(contents[i].toString());
//            }
//        }
//        if(emptySlot == -1){
//            player.getWorld().dropItemNaturally(player.getLocation(), item);
//            return;
//        }
//        player.getInventory().setItem(emptySlot, item);
//    }
//
//
//    @EventHandler(priority = EventPriority.LOW)
//    public void onPlayerPickupItem(EntityPickupItemEvent event) {
//        if (!(event.getEntity() instanceof Player player)) {
//            return;
//        }
//        ItemStack item = event.getItem().getItemStack();
//        if (!item.hasItemMeta()) {
//            return;
//        }
//        Gun gun = Gun.getGun(item);
//        if (gun == null) {
//            return;
//        }
//
//        boolean[] result = checkEquipmentLimit(player);
//        if(result[0] && result[1]){
//            return;
//        }
//        int emptySlot = -1;
//        ItemStack[] contents = player.getInventory().getContents();
//        boolean isHotbarFull = Arrays.stream(contents, 0, 9).allMatch(Objects::isNull);
//        if (isHotbarFull) {
//            return;
//        }
//
//        for (int i = 9; i < 36; i++) {
//            if (contents[i] == null) {
//                emptySlot = i;
//                break;
//            }
//        }
//        if(emptySlot == -1){
//            if(!result[0] && gun.getEquipmentType() == GunType.PRIMARY) {
//                Message.sendWarningMessage(player, "[武器AI]", "プライマリの所持制限を超えています。");
//                event.setCancelled(true);
//            }else if(!result[1] && gun.getEquipmentType() == GunType.SECONDARY){
//                Message.sendWarningMessage(player, "[武器AI]", "セカンダリの所持制限を超えています。");
//                event.setCancelled(true);
//            }
//        } else {
//            if((!result[0] && gun.getEquipmentType() == GunType.PRIMARY) || (!result[1] && gun.getEquipmentType() == GunType.SECONDARY)) {
//                player.getInventory().setItem(emptySlot, item);
//                event.getItem().remove();
//                event.setCancelled(true);
//                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
//            }
//        }
//    }
//
//    private boolean[] checkEquipmentLimit(Player player){
//        int primaryLimit = 1;
//        int secondaryLimit = 1;
//        ItemStack chestplate = player.getInventory().getChestplate();
//        if(chestplate != null && chestplate.hasItemMeta()){
//            ItemMeta meta = chestplate.getItemMeta();
//            PersistentDataContainer container = meta.getPersistentDataContainer();
//            NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
//            if(container.has(itemKey, PersistentDataType.STRING) && Objects.equals(container.get(itemKey, PersistentDataType.STRING), "tactical_vest")){
//                secondaryLimit = 2;
//            }
//        }
//        int[] hotbarCount = scanHotbar(player);
//        boolean[] result = new boolean[2];
//        // 超えてたらfalse
//        result[0] = hotbarCount[0] < primaryLimit;
//        result[1] = hotbarCount[1] < secondaryLimit;
//        return result;
//    }
//
//    private int[] scanHotbar(Player player){
//        int primaryCount = 0;
//        int secondaryCount = 0;
//
//        for (int i = 0; i < 9; i++) {
//            ItemStack item = player.getInventory().getItem(i);
//            if (item == null || !item.hasItemMeta()) {
//                continue;
//            }
//
//            Gun gun = Gun.getGun(item);
//            if (gun == null) {
//                continue;
//            }
//
//            if (gun.getEquipmentType() == GunType.PRIMARY) {
//                primaryCount++;
//            } else if (gun.getEquipmentType() == GunType.SECONDARY) {
//                secondaryCount++;
//            }
//        }
//
//        return new int[]{primaryCount, secondaryCount};
//    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        ItemMeta meta = droppedItem.getItemMeta();
        if (!droppedItem.hasItemMeta()) {
            return;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey gunSerialKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_SERIAL);

        Gun gun = Gun.getGun(droppedItem);
        if (gun == null) {
            return;
        }

        String gunSerial = container.get(gunSerialKey, PersistentDataType.STRING);

        if (!GunStatusManager.isExists(gunSerial)) {
            GunStatusManager.register(gunSerial, gun, connector);
        }

        GunStatus gunStatus = GunStatusManager.get(gunSerial);

        if (gunStatus.getMagazineAmmo() == gun.getMagazineSize()) {
            player.setWalkSpeed(0.2f);
            return;
        }
        event.setCancelled(true);
        if (!reloadingTasks.containsKey(player)) {
            startReload(player, gun, gunStatus);
        }
    }
}

