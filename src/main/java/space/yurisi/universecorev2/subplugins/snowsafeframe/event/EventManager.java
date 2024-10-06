package space.yurisi.universecorev2.subplugins.snowsafeframe.event;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.snowsafeframe.event.entity.DamageByEntityEvent;

public class EventManager {
    public EventManager(UniverseCoreV2 core) {
        init(core);
    }

    private void init(UniverseCoreV2 core) {
       Bukkit.getPluginManager().registerEvents(new DamageByEntityEvent(), core);
    }
}
