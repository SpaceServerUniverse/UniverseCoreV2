package space.yurisi.universecorev2.subplugins.universeland.manager;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeland.UniverseLand;
import space.yurisi.universecorev2.subplugins.universeland.event.block.BreakEvent;
import space.yurisi.universecorev2.subplugins.universeland.event.block.FromToEvent;
import space.yurisi.universecorev2.subplugins.universeland.event.block.PlaceEvent;
import space.yurisi.universecorev2.subplugins.universeland.event.player.BucketEvent;
import space.yurisi.universecorev2.subplugins.universeland.event.player.TouchEvent;

public class EventManager {

    public static void init(UniverseCoreV2 core) {
        core.getServer().getPluginManager().registerEvents(new BreakEvent(), core);
        core.getServer().getPluginManager().registerEvents(new BucketEvent(), core);
        //plugin.getServer().getPluginManager().registerEvents(new FromToEvent(), plugin);
        core.getServer().getPluginManager().registerEvents(new TouchEvent(), core);
        core.getServer().getPluginManager().registerEvents(new PlaceEvent(), core);
    }
}
