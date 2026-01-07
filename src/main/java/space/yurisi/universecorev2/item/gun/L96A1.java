package space.yurisi.universecorev2.item.gun;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public final class L96A1 extends Gun {

    public static final String id = "l96a1";

    public L96A1() {
        super(
                id,
                "L96A1",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.SR_BOLT;
        this.equipmentType = GunType.PRIMARY;
        this.magazineSize = 5;
        this.burst = 0;
        this.reloadTime = 5000;
        this.isZoomWalkSpeed = -0.20F;
        this.baseDamage = 15.0D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.10F;
        this.fireRate = 30;
        this.recoil = 1;
        this.spread = 5.0D;
        this.bulletNumber = 1;
        this.bulletSpeed = 2.0D;
        this.range = 500;
        this.isJumpEnabled = false;
        this.shotSound = Sound.ENTITY_IRON_GOLEM_DAMAGE;
        this.volumeSound = 10.0F;
        this.pitchSound = 0.8F;
        this.flavorText = "§7イギリスで使用される軍用ボルトアクション式スナイパーライフル";
        this.textureID = "l96a1";
        this.price = 10;
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
