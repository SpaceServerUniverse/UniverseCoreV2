package space.yurisi.universecorev2.subplugins.spaceship;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.snowsafeframe.event.entity.DamageByEntityEvent;
import space.yurisi.universecorev2.subplugins.spaceship.cache.ModelCache;
import space.yurisi.universecorev2.subplugins.spaceship.listener.LoginEventListener;
import space.yurisi.universecorev2.subplugins.spaceship.listener.QuitEventListener;

public class SpaceShip implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        ModelCache modelCache = new ModelCache();
        new SpaceShipAPI(modelCache);

        Bukkit.getPluginManager().registerEvents(new LoginEventListener(modelCache), core);
        Bukkit.getPluginManager().registerEvents(new QuitEventListener(modelCache), core);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "spaceship";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
