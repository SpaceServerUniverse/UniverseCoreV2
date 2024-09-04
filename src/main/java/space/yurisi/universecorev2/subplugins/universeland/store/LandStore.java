package space.yurisi.universecorev2.subplugins.universeland.store;

import space.yurisi.universecorev2.subplugins.universeland.UniverseLand;
import space.yurisi.universecorev2.subplugins.universeland.utils.BoundingBox;
import space.yurisi.universecorev2.subplugins.universeland.utils.Vector2;

public class LandStore {

    private boolean isSelectLand = false;

    private Vector2 startPosition = null;
    private Vector2 endPosition = null;

    private String worldName = null;

    private BoundingBox land = null;

    public boolean isSelectLand() {
        return isSelectLand;
    }

    public void setSelectLand(boolean selectLand) {
        isSelectLand = selectLand;
    }

    public Vector2 getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Vector2 startPosition) {
        this.startPosition = startPosition;
    }

    public Vector2 getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Vector2 endPosition) {
        this.endPosition = endPosition;
        setLand();
    }

    public BoundingBox getLand() {
        return land;
    }

    public void setLand() {
        if (startPosition == null || endPosition == null) return;

        sortPositionData();

        land = new BoundingBox(startPosition.getX(), startPosition.getZ(), endPosition.getX(), endPosition.getZ(), getWorldName());
    }

    public void resetLandData() {
        startPosition = null;
        endPosition = null;
        worldName = null;
        land = null;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public Long getPrice() {
        if (getLand() == null) return 0L;
        return (long) getLand().getSize() * UniverseLand.getInstance().getPluginConfig().getLandPrice();
    }

    private void sortPositionData() {
        if (startPosition == null || endPosition == null) return;

        int x1 = startPosition.x;
        int z1 = startPosition.z;

        int x2 = endPosition.x;
        int z2 = endPosition.z;

        if (x2 < x1) {
            startPosition.x = x2;
            endPosition.x = x1;
        }

        if (z2 < z1) {
            startPosition.z = z2;
            endPosition.z = z1;
        }
    }
}
