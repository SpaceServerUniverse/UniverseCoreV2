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
                30,
                210,
                0,
                2000,
                false,
                39,
                false,
                0,
                0,
                2,
                800,
                0,
                0,
                1,
                0,
                ItemStack.of(Material.DIAMOND_HOE)
        );
    }
}
