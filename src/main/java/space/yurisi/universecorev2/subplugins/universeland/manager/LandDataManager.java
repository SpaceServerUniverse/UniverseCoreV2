package space.yurisi.universecorev2.subplugins.universeland.manager;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.Land;
import space.yurisi.universecorev2.database.models.LandPermission;
import space.yurisi.universecorev2.exception.LandNotFoundException;
import space.yurisi.universecorev2.exception.LandPermissionNotFoundException;
import space.yurisi.universecorev2.subplugins.universeland.UniverseLand;
import space.yurisi.universecorev2.subplugins.universeland.store.LandData;
import space.yurisi.universecorev2.subplugins.universeland.store.LandStore;
import space.yurisi.universecorev2.subplugins.universeland.utils.BoundingBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class LandDataManager {

    private static LandDataManager instance;

    private final LinkedHashMap<UUID, LandStore> data = new LinkedHashMap<>();

    public LandDataManager() {
        instance = this;
    }

    public static LandDataManager getInstance() {
        return instance;
    }

    public LandStore getLandData(UUID uuid) {
        return data.get(uuid);
    }

    public void setLandData(UUID uuid) {
        data.put(uuid, new LandStore());
    }

    public List<LandData> getLandsData() {
        List<LandData> data = new ArrayList<>();
        List<Land> lands = null;
        DatabaseManager database = UniverseLand.getInstance().getDatabaseManager();

        try {
            lands = database.getLandRepository().getLands();
        } catch (LandNotFoundException e) {
            return data;
        }

        for (Land land : lands) {
            try {
                List<LandPermission> dbAllowedList = database.getLandPermissionRepository().getLandPermissions(land);
                List<UUID> allowedList = new ArrayList<>();
                for (LandPermission landPermission : dbAllowedList) {
                    allowedList.add(UUID.fromString(landPermission.getUuid()));
                }
                data.add(new LandData(land.getId(), UUID.fromString(land.getUuid()), land.getPrice(), new BoundingBox(land.getStart_x(), land.getStart_z(), land.getEnd_x(), land.getEnd_z(), land.getWorld_name()), allowedList));

            } catch (LandPermissionNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }

    public LandData getLandData(BoundingBox other) {
        for (LandData land : getLandsData()) {
            BoundingBox bb = land.getBoundingBox();
            if (bb.isOverlapping(other)) return land;
        }

        return null;
    }

    public boolean canAccess(Player player, BoundingBox bb){
        LandData data = getLandData(bb);
        return data == null || data.canAccess(player);
    }

    public LandData ultimateChickenHorseMaximumTheHormoneGetYutakaOzakiGreatGodUniverseWonderfulSpecialExpertPerfectHumanVerySuperGeri(Player player) {
        int x = (int) Math.round(player.getX());
        int z = (int) Math.round(player.getZ());
        return getLandData(new BoundingBox(x, z, x, z, player.getWorld().getName()));
    }
}
