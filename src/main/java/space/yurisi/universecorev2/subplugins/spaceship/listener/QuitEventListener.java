package space.yurisi.universecorev2.subplugins.spaceship.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.SpaceShip;
import space.yurisi.universecorev2.database.repositories.SpaceShipRepository;
import space.yurisi.universecorev2.subplugins.spaceship.cache.ModelCache;

public class QuitEventListener implements Listener {

    private ModelCache modelCache;

    public QuitEventListener(ModelCache modelCache){
        this.modelCache = modelCache;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        SpaceShip spaceShip = modelCache.get(player.getUniqueId());
        SpaceShipRepository spaceShipRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(SpaceShipRepository.class);

        spaceShipRepository.update(spaceShip);
    }
}
