package space.yurisi.universecorev2.subplugins.universeslot.manager;

import org.bukkit.Location;
import space.yurisi.universecorev2.subplugins.universeslot.core.SlotCore;

import java.util.HashMap;
import java.util.UUID;

public class PlayerStatusManager {

    public static final int ON_SLOT = 1;
    public static final int ON_EDIT_MODE = 1 << 1;

    private final HashMap<UUID, Integer> playerStates = new HashMap<>();

    private HashMap<UUID, Location> clickedLocations = new HashMap<>();
    public void setClickedLocation(UUID uuid, Location location) {
        clickedLocations.put(uuid, location);
    }
    public Location getClickedLocation(UUID uuid) {
        return clickedLocations.get(uuid);
    }
    public void removeClickedLocation(UUID uuid) {
        clickedLocations.remove(uuid);
    }

    private HashMap<UUID, SlotCore> playerSlotCores = new HashMap<>();
    public void setPlayerSlotCore(UUID uuid, SlotCore slotCore) {
        playerSlotCores.put(uuid, slotCore);
    }
    public SlotCore getPlayerSlotCore(UUID uuid) {
        return playerSlotCores.get(uuid);
    }
    public void removePlayerSlotCore(UUID uuid) {
        playerSlotCores.remove(uuid);
    }
    public boolean hasPlayerSlotCore(UUID uuid) {
        return playerSlotCores.containsKey(uuid);
    }

    public boolean hasFlag(UUID uuid, int flag) {
        Integer flags = playerStates.getOrDefault(uuid, 0);
        return (flags & flag) != 0;
    }

    public void addFlag(UUID uuid, int flag) {
        int currentFlags = playerStates.getOrDefault(uuid, 0);
        playerStates.put(uuid, currentFlags | flag);
    }

    public void removeFlag(UUID uuid, int flag) {
        int currentFlags = playerStates.getOrDefault(uuid, 0);
        playerStates.put(uuid, currentFlags & ~flag);
    }
}
