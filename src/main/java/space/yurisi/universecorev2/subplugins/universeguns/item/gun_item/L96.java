package space.yurisi.universecorev2.subplugins.universeguns.item.gun_item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;

public class L96 extends GunItem {

    public static final String id = "l96";

    public L96() {
        super(
                id,
                "L96",
                "SR",
                5,
                0,
                3000,
                -0.20F,
                15,
                false,
                0.0F,
                0.10F,
                30,
                1,
                5.0D,
                1,
                2.0D,
                false,
                Sound.ENTITY_IRON_GOLEM_DAMAGE,
                10.0F,
                0.8F,
                ItemStack.of(Material.DIAMOND_HOE)
        );
    }
}
