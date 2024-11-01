package space.yurisi.universecorev2.item.gun;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public final class R4C extends Gun {

    public static final String id = "r4c";

    public R4C() {
        super(
                id,
                "R4-C",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.AR;
        this.equipmentType = GunType.PRIMARY;
        this.magazineSize = 30;
        this.burst = 0;
        this.reloadTime = 2500;
        this.isZoomWalkSpeed = 0.05F;
        this.baseDamage = 2.5D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.17F;
        this.fireRate = 3;
        this.recoil = 0;
        this.spread = 0.30D;
        this.bulletNumber = 1;
        this.bulletSpeed = 3.5D;
        this.range = 500;
        this.isJumpEnabled = true;
        this.shotSound = Sound.BLOCK_PISTON_EXTEND;
        this.volumeSound = 4.0F;
        this.pitchSound = 1.8F;
        this.flavorText = "§7威力は低いものの高い連射速度でレインボーな特殊部隊に愛用されている";
    }


    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(1);
            }
            item.setItemMeta(meta);
            return item;
        };
    }
}
