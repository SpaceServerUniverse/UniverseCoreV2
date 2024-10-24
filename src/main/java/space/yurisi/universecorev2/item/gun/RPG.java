package space.yurisi.universecorev2.item.gun;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

import org.bukkit.Material;

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
        this.burst = 0;
        this.reloadTime = 10000;
        this.isZoomWalkSpeed = 0.0F;
        this.baseDamage = 10.0D;
        this.isExplosive = true;
        this.explosionRadius = 6.0F;
        this.weight = 0.10F;
        this.fireRate = 60;
        this.recoil = 1;
        this.spread = 0.5D;
        this.bulletNumber = 1;
        this.bulletSpeed = 2.0D;
        this.range = 500;
        this.isJumpEnabled = false;
        this.shotSound = Sound.ENTITY_FIREWORK_ROCKET_LAUNCH;
        this.volumeSound = 10.0F;
        this.pitchSound = 0.5F;
        this.flavorText = "§7ロケットランチャーの代名詞。爆風で広範囲にダメージを与える";
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(3);
            }
            item.setItemMeta(meta);
            return item;
        };
    }
}
