package space.yurisi.universecorev2.subplugins.universeslot.manager;

import org.bukkit.Location;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;

import java.util.HashMap;
import java.util.UUID;

public class SlotLocationManager {

    //dbから読み込んだ後メモリに保存しとくやつ

    private HashMap<Location, UUID> slotLocations;

    public SlotLocationManager() {
        slotLocations = new HashMap<>();
    }

    public boolean isSlotLocation(Location location) {
        return slotLocations.containsKey(location);
    }

    public void registerSlotLocation(Location location, UUID ownerUUID) {
        if(!isSlotLocation(location)) {
            slotLocations.put(location, ownerUUID);
        }
    }

    public void unregisterSlotLocation(Location location) {
        slotLocations.remove(location);
    }

    public UUID getOwnerUUID(Location location) {
        return slotLocations.get(location);
    }
}
