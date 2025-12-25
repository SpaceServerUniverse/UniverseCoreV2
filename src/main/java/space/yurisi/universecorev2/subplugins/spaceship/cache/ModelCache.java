package space.yurisi.universecorev2.subplugins.spaceship.cache;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.SpaceShip;
import space.yurisi.universecorev2.database.repositories.SpaceShipRepository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ModelCache {

    private final HashMap<String, SpaceShip> spaceShipHashMap = new HashMap<String, SpaceShip>();


    public void register(UUID uuid, SpaceShip spaceShip){
        String uuidString = uuid.toString();
        spaceShipHashMap.put(uuidString, spaceShip);
    }

    public SpaceShip get(UUID uuid){
        String uuidString = uuid.toString();
        return spaceShipHashMap.get(uuidString);
    }

    public void remove(UUID uuid){
        String uuidString = uuid.toString();
        spaceShipHashMap.remove(uuidString);
    }
}
