package space.yurisi.universecorev2.subplugins.spaceship.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.SpaceShip;
import space.yurisi.universecorev2.database.repositories.SpaceShipRepository;
import space.yurisi.universecorev2.exception.SpaceShipNotFoundException;
import space.yurisi.universecorev2.subplugins.spaceship.SpaceShipAPI;
import space.yurisi.universecorev2.subplugins.spaceship.cache.ModelCache;

public class LoginEventListener implements Listener {

    private ModelCache modelCache;

    public LoginEventListener(ModelCache modelCache){
        this.modelCache = modelCache;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        SpaceShipRepository spaceShipRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(SpaceShipRepository.class);

        SpaceShip spaceShip;
        try{
            spaceShip = spaceShipRepository.getByPlayer(player);
        } catch (SpaceShipNotFoundException e) {
            spaceShip = spaceShipRepository.create(player);
        }

        modelCache.register(player.getUniqueId(), spaceShip);
    }
}
