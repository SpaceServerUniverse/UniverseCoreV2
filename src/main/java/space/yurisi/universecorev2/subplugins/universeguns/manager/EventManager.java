package space.yurisi.universecorev2.subplugins.universeguns.manager;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeguns.event.GunEvent;

public class EventManager {

    public static void init(UniverseCoreV2 core) {
        core.getServer().getPluginManager().registerEvents(new GunEvent(), core);
    }
}