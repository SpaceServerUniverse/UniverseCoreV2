package space.yurisi.universecorev2.subplugins.spaceship;

import space.yurisi.universecorev2.database.models.SpaceShip;
import space.yurisi.universecorev2.subplugins.spaceship.cache.ModelCache;

import java.util.UUID;

public class SpaceShipAPI {

    private static SpaceShipAPI spaceShip;

    private ModelCache modelCache;

    public SpaceShipAPI(ModelCache modelCache){
        spaceShip = this;
        this.modelCache = modelCache;
    }

    public Long getPoint(UUID uuid){
        SpaceShip spaceShip = this.modelCache.get(uuid);
        return spaceShip.getPoint();
    }

    public void setPoint(UUID uuid, Long point){
        SpaceShip spaceShip = this.modelCache.get(uuid);
        spaceShip.setPoint(point);
        this.modelCache.register(uuid, spaceShip);
    }

    public void addPoint(UUID uuid, Long point){
        SpaceShip spaceShip = this.modelCache.get(uuid);
        Long add = spaceShip.getPoint() + point;
        spaceShip.setPoint(add);
        this.modelCache.register(uuid, spaceShip);
    }

    public static SpaceShipAPI getInstance(){
        return spaceShip;
    }
}
