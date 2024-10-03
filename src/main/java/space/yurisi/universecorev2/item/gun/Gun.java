package space.yurisi.universecorev2.item.gun;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public abstract class Gun extends CustomItem {

    protected GunType type;

    protected int magazineSize;

    protected int currentAmmo;

    protected int burst;

    /** リロード時間 (ms) */
    protected int reloadTime;

    protected boolean isReloading;

    protected long reloadEndTime;

    /** マイナスにすると動けなくなる -0.15が一番拡大するらしい */
    protected float isZoomWalkSpeed;

    protected double baseDamage;

    protected boolean isExplosive;

    protected float explosionRadius;

    /** 0~1 武器を持っているときの移動速度 */
    protected float weight;

    /** 連射速度 (クールダウンのtick) */
    protected int fireRate;

    protected int recoil;

    protected double spread;

    protected int bulletNumber;

    /** 弾速 4.0Dが限界 */
    protected double bulletSpeed;

    protected boolean isJumpEnabled;

    protected Sound shotSound;

    protected float volumeSound;

    protected float pitchSound;

    public Gun(String id, String name, ItemStack baseItem) {
        super(id, name, baseItem);
    }

    public GunType getType(){
        return this.type;
    }

    public int getMagazineSize(){
        return this.magazineSize;
    }

    public int getCurrentAmmo(){
        return this.currentAmmo;
    }

    public boolean shoot() {
        if (this.currentAmmo <= 0 || this.isReloading) {
            return false;
        }
        this.currentAmmo--;
        return true;
    }

    public int getBurst(){
        return this.burst;
    }

    public int getReloadTime(){
        return this.reloadTime;
    }

    public boolean getIsReloading(){
        return this.isReloading;
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

    public float getIsZoomWalkSpeed(){
        return this.isZoomWalkSpeed;
    }

    public double getBaseDamage(){
        return this.baseDamage;
    }

    public boolean getIsExplosive(){
        return this.isExplosive;
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

    public float getPitchSound(){
        return pitchSound;
    }

    public void updateActionBar(Player player, boolean isZoom) {
        String ammoDisplay = getAmmoDisplay();
        String messageText = (isZoom ? "ADS " : "") + ammoDisplay;
        Component message = Component.text(messageText);

        if (isReloading) {
            message = message.color(NamedTextColor.RED);
        }

        player.sendActionBar(message);
    }

    @Override
    public ItemStack getItem(){
        ItemStack item = getBaseItem().clone();
        ItemMeta meta = getBaseItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, getId());
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN), PersistentDataType.BOOLEAN, true);
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return default_setting.apply(item);
    }
}
