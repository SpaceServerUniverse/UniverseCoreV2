package space.yurisi.universecorev2.subplugins.universeslot.manager;

import org.bukkit.Location;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;

import java.util.HashMap;
import java.util.UUID;

public class SlotLocationManager {

    private HashMap<Location, UUID> slotLocations;

    private UniverseSlot main;

    public SlotLocationManager(UniverseSlot main) {
        this.main = main;
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

}
