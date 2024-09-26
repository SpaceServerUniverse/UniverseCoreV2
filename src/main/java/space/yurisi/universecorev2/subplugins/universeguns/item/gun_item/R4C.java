package space.yurisi.universecorev2.subplugins.universeguns.item.gun_item;

import org.bukkit.Material;
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
                4,
                false,
                0,
                0,
                0.18F, //0.18
                3,
                0,
                0,
                1,
                10,
                ItemStack.of(Material.DIAMOND_HOE)
        );
    }
}
