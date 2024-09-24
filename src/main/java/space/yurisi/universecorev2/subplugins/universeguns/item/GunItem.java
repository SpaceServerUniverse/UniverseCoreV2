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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class GunItem {

    private final String id;

    private final String name;

    private final int magazineSize;

    private final int maxAmmo;

    private final int burst;

    // リロード時間 (tick)
    private final int reloadTime;

    private final boolean isZoom;

    private final int baseDamage;

    private final boolean isExplosive;

    private final int explosionRadius;

    private final int explosionDamage;

    // 0~10で指定　数字が大きくなるほど重くなり移動速度低下が入る
    private final int weight;

    private final int fireRate;

    private final int recoil;

    private final int spread;

    private final int bulletNumber;

    // 弾速 弾の垂れ下がりに影響 0なら垂れ下がりなし
    private final int bulletSpeed;

    private final ItemStack baseItem;

    public GunItem(String id, String name, int magazineSize, int maxAmmo, int burst, int reloadTime, boolean isZoom, int baseDamage, boolean isExplosive, int explosionRadius, int explosionDamage, int weight, int fireRate, int recoil, int spread, int bulletNumber, int bulletSpeed, ItemStack baseItem) {
        this.id = id;
        this.name = name;
        this.magazineSize = magazineSize;
        this.maxAmmo = maxAmmo;
        this.burst = burst;
        this.reloadTime = reloadTime;
        this.isZoom = isZoom;
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
