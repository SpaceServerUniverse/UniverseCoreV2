package space.yurisi.universecorev2.subplugins.universejob.manager;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universejob.listener.player.*;
import space.yurisi.universecorev2.subplugins.universejob.UniverseJob;

public class EventManager {

    public EventManager(UniverseCoreV2 core, UniverseJob universeJob) {
        Bukkit.getServer().getPluginManager().registerEvents(new LoginEvent(universeJob), core);
    }
}
