package space.yurisi.universecorev2.subplugins.universeland.utils;

public class BoundingBox {
    private final int minX;
    private final int minZ;

    private final int maxX;
    private final int maxZ;

    private final String worldName;

    public BoundingBox(int x1, int z1, int x2, int z2, String worldName) {
        this.minX = x1;
        this.minZ = z1;
        this.maxX = x2;
        this.maxZ = z2;
        this.worldName = worldName;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public String getWorldName() {
        return worldName;
    }

    public boolean isOverlapping(BoundingBox other) {
        return worldName.equals(other.getWorldName()) && (minX <= other.maxX && maxX >= other.minX) && (minZ <= other.maxZ && maxZ >= other.minZ);
    }

    public int getSize() {
        return (maxX - minX + 1) * (maxZ - minZ + 1);
    }
}