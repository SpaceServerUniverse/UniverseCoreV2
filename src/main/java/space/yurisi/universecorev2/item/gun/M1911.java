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

public final class M1911 extends Gun {

    public static final String id = "m1911";

    public M1911(){
        super(
                id,
                "M1911",
                ItemStack.of(Material.DIAMOND_HOE)
        );

        this.type = GunType.HG;
        this.equipmentType = GunType.SECONDARY;
        this.magazineSize = 7;
        this.burst = 0;
        this.reloadTime = 800;
        this.isZoomWalkSpeed = 0.23F;
        this.baseDamage = 2.0D;
        this.isExplosive = false;
        this.explosionRadius = 0.0F;
        this.weight = 0.20F;
        this.fireRate = 6;
        this.recoil = 0;
        this.spread = 0.05D;
        this.bulletNumber = 1;
        this.bulletSpeed = 3.5D;
        this.range = 500;
        this.isJumpEnabled = true;
        this.shotSound = Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR;
        this.volumeSound = 5.0F;
        this.pitchSound = 2.0F;
        this.flavorText = "§7アメリカで開発された自動拳銃。第一次世界大戦時代に生まれ、現在でも多くの国で使用されている";
        this.textureID = "m1911";
        this.price = 1;
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
