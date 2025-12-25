package space.yurisi.universecorev2.item.gun;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

import org.bukkit.Material;

public final class F2 extends Gun {

    public static final String id = "f2";

    public F2() {
        super(
                id,
                "F2",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.AR;
        this.equipmentType = GunType.PRIMARY;
        this.magazineSize = 30;
        this.burst = 2;
        this.reloadTime = 3500;
        this.isZoomWalkSpeed = 0.15F;
        this.baseDamage = 1.5D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.17F;
        this.fireRate = 8;
        this.recoil = 0;
        this.spread = 0.06D;
        this.bulletNumber = 1;
        this.bulletSpeed = 3.5D;
        this.range = 500;
        this.isJumpEnabled = true;
        this.shotSound = Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR;
        this.volumeSound = 5.0F;
        this.pitchSound = 0.8F;
        this.flavorText = "§7フランスで開発された3点バースト式のアサルトライフル。高いレートと命中精度が特徴";
        this.textureID = "f2";
        this.price = 4;
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
