package space.yurisi.universecorev2.utils.material_type;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Armor {

    public static final Material[] armors = {

            Material.LEATHER_BOOTS,
            Material.IRON_BOOTS,
            Material.GOLDEN_BOOTS,
            Material.DIAMOND_BOOTS,
            Material.CHAINMAIL_BOOTS,
            Material.NETHERITE_BOOTS,

            Material.LEATHER_LEGGINGS,
            Material.IRON_LEGGINGS,
            Material.GOLDEN_LEGGINGS,
            Material.DIAMOND_LEGGINGS,
            Material.CHAINMAIL_LEGGINGS,
            Material.NETHERITE_LEGGINGS,

            Material.LEATHER_CHESTPLATE,
            Material.IRON_CHESTPLATE,
            Material.GOLDEN_CHESTPLATE,
            Material.DIAMOND_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE,
            Material.NETHERITE_CHESTPLATE,

            Material.TURTLE_HELMET,
            Material.LEATHER_HELMET,
            Material.IRON_HELMET,
            Material.GOLDEN_HELMET,
            Material.DIAMOND_HELMET,
            Material.CHAINMAIL_HELMET,
            Material.NETHERITE_HELMET
    };

    public static boolean isArmor(Material material){
        List<Material> list = Arrays.stream(armors).toList();
        return list.contains(material);
    }
}
