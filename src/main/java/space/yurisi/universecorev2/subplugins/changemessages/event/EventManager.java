package space.yurisi.universecorev2.subplugins.changemessages.event;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.subplugins.changemessages.ChangeMessages;
import space.yurisi.universecorev2.subplugins.changemessages.event.player.DeathEvent;
import space.yurisi.universecorev2.subplugins.changemessages.event.player.JoinEvent;
import space.yurisi.universecorev2.subplugins.changemessages.event.player.QuitEvent;
import space.yurisi.universecorev2.UniverseCoreV2;

public class EventManager {

    private ChangeMessages main;

    private UniverseCoreV2 core;

    public EventManager(UniverseCoreV2 core, ChangeMessages main){
        this.main = main;
        this.core = core;
        init();
    }

    private void init(){
        Bukkit.getPluginManager().registerEvents(new JoinEvent(this.main.getConfigClass(), core), core);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), core);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(), core);
    }
}
