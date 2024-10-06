package space.yurisi.universecorev2.item.gun;

import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public final class F2 extends Gun {

    public static final String id = "f2";

    public F2() {
        super(
                id,
                "F2",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.AR;
        this.magazineSize = 30;
        this.burst = 2;
        this.reloadTime = 2500;
        this.isZoomWalkSpeed = 0.15F;
        this.baseDamage = 1.5D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.17F;
        this.fireRate = 7;
        this.recoil = 0;
        this.spread = 0.1D;
        this.bulletNumber = 1;
        this.bulletSpeed = 3.5D;
        this.isJumpEnabled = true;
        this.shotSound = Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR;
        this.volumeSound = 5.0F;
        this.pitchSound = 0.8F;
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(5);
            }
            item.setItemMeta(meta);
            return item;
        };
    }
}
