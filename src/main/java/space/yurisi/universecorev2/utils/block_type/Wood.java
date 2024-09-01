package space.yurisi.universecore.utils.block_type;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Wood {
    private static final Material[] woods = {
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_CHERRY_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_MANGROVE_LOG,
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.BIRCH_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.CHERRY_LOG,
            Material.DARK_OAK_LOG,
            Material.MANGROVE_LOG

    };

    public static boolean isWood(Material material){
        List<Material> list = Arrays.stream(woods).toList();
        return list.contains(material);
    }
}
