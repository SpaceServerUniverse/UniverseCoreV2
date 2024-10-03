package space.yurisi.universecorev2.subplugins.universeguns.item.gun_item;

import org.bukkit.Material;
import org.bukkit.Sound;
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
                0,
                10000,
                0.0F,
                10,
                true,
                6.0F,
                0.09F,
                60,
                1,
                5.0D,
                1,
                2.0D,
                false,
                Sound.ENTITY_FIREWORK_ROCKET_LAUNCH,
                10.0F,
                0.5F,
                ItemStack.of(Material.DIAMOND_HOE)
        );
    }
}
