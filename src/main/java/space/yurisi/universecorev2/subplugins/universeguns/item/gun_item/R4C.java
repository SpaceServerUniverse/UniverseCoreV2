package space.yurisi.universecorev2.subplugins.universeguns.item.gun_item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;

public class R4C extends GunItem {

    public static final String id = "r4c";

    public R4C() {
        super(
                id,
                "R4-C",
                "AR",
                30,
                30,
                2000,
                0.05F,
                4.0D,
                false,
                0.0F,
                0.17F,
                3,
                0,
                0.20D,
                1,
                3.5D,
                true,
                Sound.BLOCK_PISTON_EXTEND,
                4.0F,
                1.8F,
                ItemStack.of(Material.DIAMOND_HOE)
        );
    }
}
