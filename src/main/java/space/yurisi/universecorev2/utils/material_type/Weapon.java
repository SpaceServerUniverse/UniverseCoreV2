package space.yurisi.universecorev2.utils.material_type;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Weapon {
    public static final Material[] weapons = {

            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.IRON_SWORD,
            Material.GOLDEN_SWORD,
            Material.DIAMOND_SWORD,
            Material.NETHERITE_SWORD,

            Material.WOODEN_AXE,
            Material.STONE_AXE,
            Material.IRON_AXE,
            Material.GOLDEN_AXE,
            Material.DIAMOND_AXE,
            Material.NETHERITE_AXE,

            Material.WOODEN_HOE,
            Material.STONE_HOE,
            Material.IRON_HOE,
            Material.GOLDEN_HOE,
            Material.DIAMOND_HOE,
            Material.NETHERITE_HOE,

            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.NETHERITE_PICKAXE,

            Material.WOODEN_SHOVEL,
            Material.STONE_SHOVEL,
            Material.IRON_SHOVEL,
            Material.GOLDEN_SHOVEL,
            Material.DIAMOND_SHOVEL,
            Material.NETHERITE_SHOVEL,

            Material.BOW,
            Material.CROSSBOW,

            Material.TRIDENT,
            Material.MACE,

            Material.FISHING_ROD,

            Material.ELYTRA
    };

    public static boolean isWeapon(Material material){
        List<Material> list = Arrays.stream(weapons).toList();
        return list.contains(material);
    }
}
