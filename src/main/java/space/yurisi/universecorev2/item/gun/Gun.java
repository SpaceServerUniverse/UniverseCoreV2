package space.yurisi.universecorev2.item.gun;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

import java.util.List;
import java.util.UUID;

public abstract class Gun extends CustomItem {

    protected GunType type;

    protected int magazineSize;

    protected int burst;

    /** マイナスにすると動けなくなる -0.15が一番拡大するらしい */
    protected float isZoomWalkSpeed;

    protected double baseDamage;

    protected boolean isExplosive;

    protected float explosionRadius;

    /** 0~1 武器を持っているときの移動速度 */
    protected float weight;

    /** 連射速度 (クールダウンのtick) */
    protected int fireRate;

    /** リロード時間 (ms) */
    protected int reloadTime;

    protected int recoil;

    protected double spread;

    protected int bulletNumber;

    /** 弾速 4.0Dが限界 */
    protected double bulletSpeed;

    protected long range;

    protected boolean isJumpEnabled;

    protected Sound shotSound;

    protected float volumeSound;

    protected float pitchSound;

    protected String flavorText;

    public Gun(String id, String name, ItemStack baseItem) {
        super(id, name, baseItem);
    }

    public GunType getType(){
        return this.type;
    }

    public int getMagazineSize(){
        return this.magazineSize;
    }


    public int getBurst(){
        return this.burst;
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

    public int getReloadTime(){
        return this.reloadTime;
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

    public long getRange() {
        return range;
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

    @Override
    public ItemStack getItem(){
        ItemStack item = getBaseItem().clone();
        ItemMeta meta = getBaseItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, getId());
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN), PersistentDataType.BOOLEAN, true);
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_SERIAL), PersistentDataType.STRING, UUID.randomUUID().toString());
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        String category = "§7カテゴリ: ";
        switch (type){
            case AR:
                category += "アサルトライフル";
                break;
            case SMG:
                category += "サブマシンガン";
                break;
            case SG:
                category += "ショットガン";
                break;
            case SR:
                category += "スナイパーライフル";
                break;
            case HG:
                category += "ハンドガン";
                break;
            case LMG:
                category += "軽機関銃";
                break;
            case EX:
                category += "特殊系";
                break;
        }
        List<Component> lore = List.of(
                Component.text(category),
                Component.text("§7マガジンサイズ: " + magazineSize),
                Component.text("§7リロード時間: " + reloadTime/1000 + "s"),
                Component.text(flavorText)
        );
        meta.lore(lore);
        return default_setting.apply(item);
    }

    public static boolean isGun(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();

        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        NamespacedKey gunKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN);
        NamespacedKey gunSerialKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_SERIAL);

        if(!container.has(itemKey, PersistentDataType.STRING)
                || !container.has(gunKey, PersistentDataType.BOOLEAN)
                || !container.has(gunSerialKey, PersistentDataType.STRING)
        ){
            return false;
        }
        return true;
    }
}
