package space.yurisi.universecorev2.subplugins.birthdaycard.event;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.birthdaycard.event.player.JoinEvent;

public class EventManager {
    public EventManager(UniverseCoreV2 core) {
        init(core);
    }

    private void init(UniverseCoreV2 core) {
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), core);
    }
}
