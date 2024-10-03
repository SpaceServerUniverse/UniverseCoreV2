package space.yurisi.universecorev2.item.gun;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public final class RPG extends Gun {

    public static final String id = "rpg";

    public RPG() {
        super(
                id,
                "RPG",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.EX;
        this.magazineSize = 1;
        this.currentAmmo = 1;
        this.burst = 0;
        this.reloadTime = 10000;
        this.isReloading = false;
        this.reloadEndTime = 0;
        this.isZoomWalkSpeed = 0.0F;
        this.baseDamage = 10.0D;
        this.isExplosive = true;
        this.explosionRadius = 6.0F;
        this.weight = 0.10F;
        this.fireRate = 60;
        this.recoil = 1;
        this.spread = 5.0D;
        this.bulletNumber = 1;
        this.bulletSpeed = 2.0D;
        this.isJumpEnabled = false;
        this.shotSound = Sound.ENTITY_FIREWORK_ROCKET_LAUNCH;
        this.volumeSound = 10.0F;
        this.pitchSound = 0.5F;
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> item;
    }
}
