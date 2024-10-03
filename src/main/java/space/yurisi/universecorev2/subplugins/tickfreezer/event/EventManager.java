package space.yurisi.universecorev2.subplugins.tickfreezer.event;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.tickfreezer.event.block.FadeEvent;
import space.yurisi.universecorev2.subplugins.tickfreezer.file.Config;


public class EventManager {
    public EventManager(UniverseCoreV2 core) {
        init(core);
    }

    private void init(UniverseCoreV2 core) {
        Bukkit.getPluginManager().registerEvents(new FadeEvent(new Config(core)), core);
    }
}
