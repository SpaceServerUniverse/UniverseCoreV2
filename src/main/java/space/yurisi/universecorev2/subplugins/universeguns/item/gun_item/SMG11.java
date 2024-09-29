package space.yurisi.universecorev2.subplugins.universeguns.item.gun_item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;

public class SMG11 extends GunItem {

    public static final String id = "smg11";

    public SMG11() {
        super(
                id,
                "SMG-11",
                "SMG",
                16,
                16,
                800,
                0.17F,
                1.5,
                false,
                0.0F,
                0.19F,
                0,
                0,
                0.0D,
                1,
                3.0D,
                true,
                Sound.ENTITY_FIREWORK_ROCKET_BLAST,
                5.0F,
                0.5F,
                ItemStack.of(Material.DIAMOND_HOE)
        );
    }
}
