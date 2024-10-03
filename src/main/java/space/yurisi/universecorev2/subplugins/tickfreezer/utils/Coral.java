package space.yurisi.universecorev2.subplugins.tickfreezer.utils;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Coral {
    private static final Material[] corals = {
            Material.BRAIN_CORAL,
            Material.BUBBLE_CORAL,
            Material.TUBE_CORAL,
            Material.HORN_CORAL,
            Material.FIRE_CORAL,
            Material.BRAIN_CORAL_BLOCK,
            Material.BUBBLE_CORAL_BLOCK,
            Material.TUBE_CORAL_BLOCK,
            Material.HORN_CORAL_BLOCK,
            Material.FIRE_CORAL_BLOCK,
            Material.BRAIN_CORAL_FAN,
            Material.BUBBLE_CORAL_FAN,
            Material.TUBE_CORAL_FAN,
            Material.FIRE_CORAL_FAN,
            Material.HORN_CORAL_FAN,
            Material.BRAIN_CORAL_WALL_FAN,
            Material.BUBBLE_CORAL_WALL_FAN,
            Material.TUBE_CORAL_WALL_FAN,
            Material.FIRE_CORAL_WALL_FAN,
            Material.HORN_CORAL_WALL_FAN,
    };

    public static boolean isCoral(Material material) {
        List<Material> list = Arrays.stream(corals).toList();
        return list.contains(material);
    }
}
