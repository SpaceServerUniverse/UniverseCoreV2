package space.yurisi.universecorev2.subplugins.universeslot.listener;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.listener.block.BreakEvent;
import space.yurisi.universecorev2.subplugins.universeslot.listener.block.ShelfInteractEvent;
import space.yurisi.universecorev2.subplugins.universeslot.listener.player.LogoutEvent;
import space.yurisi.universecorev2.subplugins.universeslot.listener.player.SlotPlayerMoveProtect;
import space.yurisi.universecorev2.subplugins.universeslot.listener.player.SlotPlayerTeleportEvent;

public class EventManager {

    public EventManager(UniverseCoreV2 core, UniverseSlot main) {
        init(core, main);
    }

    private void init(UniverseCoreV2 core, UniverseSlot main) {
        core.getServer().getPluginManager().registerEvents(new ShelfInteractEvent(main), core);
        core.getServer().getPluginManager().registerEvents(new LogoutEvent(main), core);
        core.getServer().getPluginManager().registerEvents(new BreakEvent(), core);
        core.getServer().getPluginManager().registerEvents(new SlotPlayerMoveProtect(), core);
        core.getServer().getPluginManager().registerEvents(new SlotPlayerTeleportEvent(), core);
    }
}
