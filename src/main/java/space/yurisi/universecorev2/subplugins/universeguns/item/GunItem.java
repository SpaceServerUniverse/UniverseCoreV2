package space.yurisi.universecorev2.subplugins.universeguns.item;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.item.gun_item.R4C;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class GunItem {

    private final String id;

    private final String name;

    private final String type;

    private final int magazineSize;

    private final int burst;

    // リロード時間 (tick)
    private final int reloadTime;

    // マイナスにすると動けなくなる -0.15が一番拡大するらしい
    private final float isZoomWalkSpeed;

    private final int baseDamage;

    private final boolean isExplosive;

    private final int explosionRadius;

    private final int explosionDamage;

    // 0~1 武器を持っているときの移動速度
    private final float weight;

    // 連射速度 (クールダウンのtick)
    private final int fireRate;

    private final int recoil;

    private final int spread;

    private final int bulletNumber;

    // 弾速 弾の垂れ下がりに影響 0なら垂れ下がりなし
    private final int bulletSpeed;

    private final ItemStack baseItem;

    public GunItem(String id, String name, String type, int magazineSize, int burst, int reloadTime, float isZoomWalkSpeed, int baseDamage, boolean isExplosive, int explosionRadius, int explosionDamage, float weight, int fireRate, int recoil, int spread, int bulletNumber, int bulletSpeed, ItemStack baseItem) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.magazineSize = magazineSize;
        this.burst = burst;
        this.reloadTime = reloadTime;
        this.isZoomWalkSpeed = isZoomWalkSpeed;
        this.baseDamage = baseDamage;
        this.isExplosive = isExplosive;
        this.explosionRadius = explosionRadius;
        this.explosionDamage = explosionDamage;
        this.weight = weight;
        this.fireRate = fireRate;
        this.recoil = recoil;
        this.spread = spread;
        this.bulletNumber = bulletNumber;
        this.bulletSpeed = bulletSpeed;
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

    public int getBurst() {
        return burst;
    }

    public int getReloadTime() {
        return reloadTime;
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

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public int getExplosionDamage() {
        return explosionDamage;
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

    public int getSpread() {
        return spread;
    }

    public int getBulletNumber() {
        return bulletNumber;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public ItemStack getBaseItem() {
        return baseItem;
    }

    public ItemStack getItem() {
        ItemStack item = baseItem.clone();
        ItemMeta meta = baseItem.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, getId());
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return item;
    }
}
