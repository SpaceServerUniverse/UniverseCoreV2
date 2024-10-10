package space.yurisi.universecorev2.subplugins.birthdaycard.event;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.birthdaycard.event.player.JoinEvent;

public class EventManager {
    private UniverseCoreV2 core;

    public EventManager(UniverseCoreV2 core) {
        init();
    }

    private void init() {

        Bukkit.getPluginManager().registerEvents(new JoinEvent(), core);
    }
}
