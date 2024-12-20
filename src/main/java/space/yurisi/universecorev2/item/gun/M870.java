package space.yurisi.universecorev2.item.gun;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

import org.bukkit.Material;

public final class M870 extends Gun {

    public static final String id = "m870";

    public M870() {
        super(
                id,
                "M870",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.SG;
        this.equipmentType = GunType.PRIMARY;
        this.magazineSize = 7;
        this.burst = 0;
        this.reloadTime = 4000;
        this.isZoomWalkSpeed = 0.14F;
        this.baseDamage = 3.5D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.16F;
        this.fireRate = 20;
        this.recoil = 1;
        this.spread = 0.2D;
        this.bulletNumber = 8;
        this.bulletSpeed = 2.5D;
        this.range = 20;
        this.isJumpEnabled = true;
        this.shotSound = Sound.ITEM_SHIELD_BREAK;
        this.volumeSound = 5.0F;
        this.pitchSound = 0.6F;
        this.flavorText = "§7ポンプアクション式ショットガン。有効射程が長いが連射速度は遅い";
        this.textureNumber = 6;
        this.price = 5;
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(textureNumber);
            }
            item.setItemMeta(meta);
            return item;
        };
    }
}
