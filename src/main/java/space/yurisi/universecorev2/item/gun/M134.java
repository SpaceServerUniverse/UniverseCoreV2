package space.yurisi.universecorev2.item.gun;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

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
        this.bulletSpeed = 4.0D;
        this.range = 500;
        this.isJumpEnabled = false;
        this.shotSound = Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR;
        this.volumeSound = 0.3F;
        this.pitchSound = 1.5F;
        this.flavorText = "§7ヘリコプターなどに搭載される6連装のガトリング式機関銃を改造した携行型ミニガン。圧倒的な弾幕を誇るが、重すぎてまともに動けない。";
        this.textureID = "m134";
        this.price = 20;
    }

    @Override
    protected void registerItemFunction(){
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setItemModel(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_ITEM_MODEL));
            }
            item.setItemMeta(meta);
            CustomModelData modelData = CustomModelData.customModelData().addString(textureID).build();
            item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, modelData);
            return item;
        };
    }

}
