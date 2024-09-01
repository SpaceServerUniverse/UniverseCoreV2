package space.yurisi.universecore.utils.block_type;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Flower {
    private static final Material[] flowers = {
            Material.DANDELION,
            Material.POPPY,
            Material.ALLIUM,
            Material.AZURE_BLUET,
            Material.RED_TULIP,
            Material.ORANGE_TULIP,
            Material.WHITE_TULIP,
            Material.PINK_TULIP,
            Material.OXEYE_DAISY,
            Material.CORNFLOWER,
            Material.LILY_OF_THE_VALLEY
    };

    public static boolean isFlower(Material material){
        List<Material> list = Arrays.stream(flowers).toList();
        return list.contains(material);
    }
}
