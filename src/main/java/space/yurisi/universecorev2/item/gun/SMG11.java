package space.yurisi.universecorev2.item.gun;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
        this.equipmentType = GunType.SECONDARY;
        this.magazineSize = 16;
        this.burst = 0;
        this.reloadTime = 800;
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
        this.range = 500;
        this.isJumpEnabled = true;
        this.shotSound = Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR;
        this.volumeSound = 3.0F;
        this.pitchSound = 0.8F;
        this.flavorText = "§7随一の連射速度と軽量さが特徴のサブマシンガン。覗かないとまともに当たらない";
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(4);
            }
            item.setItemMeta(meta);
            return item;
        };
    }

}
