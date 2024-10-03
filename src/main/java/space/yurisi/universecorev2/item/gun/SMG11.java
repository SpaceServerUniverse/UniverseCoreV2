package space.yurisi.universecorev2.item.gun;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public final class SMG11 extends Gun {

    public static final String id = "smg11";

    public SMG11() {
        super(
                id,
                "SMG-11",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.SMG;
        this.magazineSize = 16;
        this.currentAmmo = 16;
        this.burst = 0;
        this.reloadTime = 800;
        this.isReloading = false;
        this.reloadEndTime = 0;
        this.isZoomWalkSpeed = 0.17F;
        this.baseDamage = 1.0D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.19F;
        this.fireRate = 1;
        this.recoil = 0;
        this.spread = 1.0D;
        this.bulletNumber = 1;
        this.bulletSpeed = 3.0D;
        this.isJumpEnabled = true;
        this.shotSound = Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR;
        this.volumeSound = 3.0F;
        this.pitchSound = 0.8F;
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> item;
    }

}
