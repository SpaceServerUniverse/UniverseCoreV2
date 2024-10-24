package space.yurisi.universecorev2.item.gun;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

import org.bukkit.Material;

public final class M1911 extends Gun {

    public static final String id = "m1911";

    public M1911(){
        super(
                id,
                "M1911",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.HG;
        this.magazineSize = 7;
        this.burst = 0;
        this.reloadTime = 800;
        this.isZoomWalkSpeed = 0.20F;
        this.baseDamage = 2.0D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.20F;
        this.fireRate = 5;
        this.recoil = 0;
        this.spread = 0.05D;
        this.bulletNumber = 1;
        this.bulletSpeed = 3.5D;
        this.range = 500;
        this.isJumpEnabled = true;
        this.shotSound = Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR;
        this.volumeSound = 5.0F;
        this.pitchSound = 2.0F;
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(7);
            }
            item.setItemMeta(meta);
            return item;
        };

    }
}
