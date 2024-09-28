package space.yurisi.universecorev2.subplugins.universeguns.item.gun_item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;

public class RPG extends GunItem {

    public static final String id = "rpg";

    public RPG() {
        super(
                id,
                "RPG",
                "EX",
                1,
                1,
                10000,
                0.0F,
                10,
                true,
                6.0F,
                0.09F,
                60,
                1,
                3.0D,
                1,
                2.0D,
                false,
                ItemStack.of(Material.DIAMOND_HOE)
        );
    }
}
