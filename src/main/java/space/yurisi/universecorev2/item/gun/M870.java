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
        this.magazineSize = 7;
        this.burst = 0;
        this.reloadTime = 4000;
        this.isZoomWalkSpeed = 0.14F;
        this.baseDamage = 3.0D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.16F;
        this.fireRate = 20;
        this.recoil = 1;
        this.spread = 0.2D;
        this.bulletNumber = 8;
        this.bulletSpeed = 3.0D;
        this.range = 20;
        this.isJumpEnabled = false;
        this.shotSound = Sound.ITEM_SHIELD_BREAK;
        this.volumeSound = 5.0F;
        this.pitchSound = 0.6F;
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(6);
            }
            item.setItemMeta(meta);
            return item;
        };
    }
}
