package space.yurisi.universecorev2.item.gun;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

import static org.bukkit.Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR;

public class M134 extends Gun {

    public static final String id = "M134_minigun";

    public M134() {
        super(
                id,
                "M134 Minigun",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.LMG;
        this.equipmentType = GunType.PRIMARY;
        this.magazineSize = 500;
        this.burst = 0;
        this.reloadTime = 20000;
        this.isZoomWalkSpeed = -0.01F;
        this.baseDamage = 1.0D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.00F;
        this.fireRate = 1;
        this.recoil = 1;
        this.spread = 4.0D;
        this.bulletNumber = 1;
        this.bulletSpeed = 6.0D;
        this.range = 500;
        this.isJumpEnabled = false;
        this.shotSound = Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR;
        this.volumeSound = 10.0F;
        this.pitchSound = 1.5F;
        this.flavorText = "§7ヘリコプターなどに搭載される6連装のガトリング式機関銃を改造した携行型ミニガン。圧倒的な弾幕を誇るが、重すぎてまともに動けない。";
        this.textureNumber = 4;
        this.price = 20;
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                //TODO: テクスチャ未実装
//                meta.setCustomModelData(textureNumber);
            }
            item.setItemMeta(meta);
            return item;
        };
    }

}
