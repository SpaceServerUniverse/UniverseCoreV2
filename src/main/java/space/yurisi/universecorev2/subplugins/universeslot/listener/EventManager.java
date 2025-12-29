package space.yurisi.universecorev2.subplugins.universeslot.listener;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.listener.block.ShelfInteractEvent;
import space.yurisi.universecorev2.subplugins.universeslot.listener.player.LogoutEvent;

public class EventManager {

    public EventManager(UniverseCoreV2 core, UniverseSlot main) {
        init(core, main);
    }

    private void init(UniverseCoreV2 core, UniverseSlot main) {
        Bukkit.getPluginManager().registerEvents(new ShelfInteractEvent(main), core);
        Bukkit.getPluginManager().registerEvents(new LogoutEvent(main), core);
    }
}
