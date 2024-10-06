package space.yurisi.universecorev2.item.gun;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public final class L96A1 extends Gun {

    public static final String id = "l96a1";

    public L96A1() {
        super(
                id,
                "L96A1",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.SR;
        this.magazineSize = 5;
        this.burst = 0;
        this.reloadTime = 5000;
        this.isZoomWalkSpeed = -0.20F;
        this.baseDamage = 15.0D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.10F;
        this.fireRate = 60;
        this.recoil = 1;
        this.spread = 5.0D;
        this.bulletNumber = 1;
        this.bulletSpeed = 2.0D;
        this.isJumpEnabled = false;
        this.shotSound = Sound.ENTITY_IRON_GOLEM_DAMAGE;
        this.volumeSound = 10.0F;
        this.pitchSound = 0.8F;
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(2);
            }
            item.setItemMeta(meta);
            return item;
        };
    }
}
