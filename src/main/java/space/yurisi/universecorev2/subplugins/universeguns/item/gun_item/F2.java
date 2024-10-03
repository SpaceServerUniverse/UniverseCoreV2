package space.yurisi.universecorev2.subplugins.universeguns.item.gun_item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;

public class F2 extends GunItem {

    public static final String id = "f2";

    public F2() {
        super(
                id,
                "F2",
                "SR",
                30,
                2,
                2500,
                0.15F,
                3.5D,
                false,
                0.0F,
                0.17F,
                4,
                0,
                0.3D,
                1,
                3.5D,
                true,
                Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR,
                5.0F,
                1.0F,
                ItemStack.of(Material.DIAMOND_HOE)
        );
    }
}
