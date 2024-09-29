package space.yurisi.universecorev2.subplugins.universeguns.item;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;

public abstract class GunItem {

    private final String id;

    private final String name;

    private final String type;

    private final int magazineSize;

    private int currentAmmo;

    private final int burst;

    // リロード時間 (tick)
    private final int reloadTime;

    private boolean isReloading;

    private long reloadEndTime;

    // マイナスにすると動けなくなる -0.15が一番拡大するらしい
    private final float isZoomWalkSpeed;

    private final int baseDamage;

    private final boolean isExplosive;

    private final float explosionRadius;

    // 0~1 武器を持っているときの移動速度
    private final float weight;

    // 連射速度 (クールダウンのtick)
    private final int fireRate;

    private final int recoil;

    private final double spread;

    private final int bulletNumber;

    // 弾速 4.0Dが限界
    private final double bulletSpeed;

    private final boolean isJumpEnabled;

    private final Sound shotSound;

    private final float volumeSound;

    private final float pitchSound;

    private final ItemStack baseItem;

    public GunItem(String id, String name, String type, int magazineSize, int burst, int reloadTime, float isZoomWalkSpeed, int baseDamage, boolean isExplosive, float explosionRadius, float weight, int fireRate, int recoil, double spread, int bulletNumber, double bulletSpeed, boolean isJumpEnabled, Sound shotSound, float volumeSound, float pitchSound, ItemStack baseItem) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.magazineSize = magazineSize;
        this.currentAmmo = magazineSize;
        this.burst = burst;
        this.reloadTime = reloadTime;
        this.isReloading = false;
        this.isZoomWalkSpeed = isZoomWalkSpeed;
        this.baseDamage = baseDamage;
        this.isExplosive = isExplosive;
        this.explosionRadius = explosionRadius;
        this.weight = weight;
        this.fireRate = fireRate;
        this.recoil = recoil;
        this.spread = spread;
        this.bulletNumber = bulletNumber;
        this.bulletSpeed = bulletSpeed;
        this.isJumpEnabled = isJumpEnabled;
        this.shotSound = shotSound;
        this.volumeSound = volumeSound;
        this.pitchSound = pitchSound;
        this.baseItem = baseItem;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void setCurrentAmmo(int ammo) {
        this.currentAmmo = ammo;
    }

    public boolean shoot() {
        if (currentAmmo <= 0 || isReloading) {
            return false;
        }
        currentAmmo--;
        return true;
    }

    public int getBurst() {
        return burst;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public boolean getIsReloading() {
        return isReloading;
    }

    public void startReload() {
        this.isReloading = true;
        this.reloadEndTime = System.currentTimeMillis() + reloadTime;
    }

    public void finishReload() {
        this.isReloading = false;
        this.currentAmmo = magazineSize;
    }

    public void cancelReload() {
        this.isReloading = false;

    }

    public long getReloadRemainingTime() {
        return Math.max(0, reloadEndTime - System.currentTimeMillis());
    }

    public String getAmmoDisplay() {
        if (isReloading) {
            long remainingTime = getReloadRemainingTime();
            double seconds = remainingTime / 1000.0;
            return String.format("<< Reload %.1f >>", seconds);
        }
        return String.format("<< %d/%d >>", currentAmmo, magazineSize);
    }

    public float getIsZoomWalkSpeed() {
        return isZoomWalkSpeed;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public boolean getIsExplosive() {
        return isExplosive;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public float getWeight() {
        return weight;
    }

    public int getFireRate() {
        return fireRate;
    }

    public int getRecoil() {
        return recoil;
    }

    public double getSpread() {
        return spread;
    }

    public int getBulletNumber() {
        return bulletNumber;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public boolean getIsJumpEnabled() {
        return isJumpEnabled;
    }

    public Sound getShotSound() {
        return shotSound;
    }

    public float getVolumeSound() {
        return volumeSound;
    }

    public float getPitchSound() {
        return pitchSound;
    }

    public ItemStack getBaseItem() {
        return baseItem;
    }

    public ItemStack getItem() {
        ItemStack item = baseItem.clone();
        ItemMeta meta = baseItem.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, getId());
        meta.displayName(Component.text(name + "   " + getAmmoDisplay()));
        item.setItemMeta(meta);
        return item;
    }
}
