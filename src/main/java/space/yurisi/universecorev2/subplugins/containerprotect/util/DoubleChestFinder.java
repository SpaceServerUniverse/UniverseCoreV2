package space.yurisi.universecorev2.subplugins.containerprotect.util;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Chest;

public class DoubleChestFinder {

    public static BlockFace getNeighborBlockFace(Chest chest) {
        if (chest.getType() == Chest.Type.SINGLE) return null;

        BlockFace face = chest.getFacing();
        return switch (face) {
            case NORTH -> chest.getType() == Chest.Type.LEFT ? BlockFace.EAST : BlockFace.WEST;
            case SOUTH -> chest.getType() == Chest.Type.LEFT ? BlockFace.WEST : BlockFace.EAST;
            case EAST -> chest.getType() == Chest.Type.LEFT ? BlockFace.SOUTH : BlockFace.NORTH;
            case WEST -> chest.getType() == Chest.Type.LEFT ? BlockFace.NORTH : BlockFace.SOUTH;
            default -> null;
        };
    }
}
