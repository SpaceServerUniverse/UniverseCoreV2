package space.yurisi.universecorev2.utils.material_type;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Armor {
    public static final Material[] armors = {
            Material.IRON_BOOTS,
            Material.DIAMOND_BOOTS,
            Material.LEATHER_BOOTS,
            Material.NETHERITE_BOOTS,
            Material.GOLDEN_BOOTS,
            Material.CHAINMAIL_BOOTS,
            Material.DIAMOND_LEGGINGS,
            Material.GOLDEN_LEGGINGS,
            Material.LEATHER_LEGGINGS,
            Material.IRON_LEGGINGS,
            Material.NETHERITE_LEGGINGS,
            Material.CHAINMAIL_LEGGINGS,
            Material.IRON_CHESTPLATE,
            Material.LEATHER_CHESTPLATE,
            Material.DIAMOND_CHESTPLATE,
            Material.GOLDEN_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE,
            Material.LEATHER_HELMET,
            Material.TURTLE_HELMET,
            Material.IRON_HELMET,
            Material.DIAMOND_HELMET,
            Material.GOLDEN_HELMET,
            Material.CHAINMAIL_HELMET
    };

    public static boolean isArmor(Material material){
        List<Material> list = Arrays.stream(armors).toList();
        return list.contains(material);
    }
}
