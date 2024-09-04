package space.yurisi.universecorev2.subplugins.rankcounter.manager;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.rankcounter.RankCounter;
import space.yurisi.universecorev2.subplugins.rankcounter.event.KillDeathCountEvents;
import space.yurisi.universecorev2.subplugins.rankcounter.event.LifeCountEvents;
import space.yurisi.universecorev2.subplugins.rankcounter.event.OreCountEvents;
import space.yurisi.universecorev2.subplugins.rankcounter.event.PlayerCountEvents;
import space.yurisi.universecorev2.subplugins.rankcounter.event.player.LoginEvent;
import space.yurisi.universecorev2.subplugins.rankcounter.event.player.QuitEvent;

public class EventManager {

    public EventManager(UniverseCoreV2 core, CounterModelManager manager){
        init(core,manager);
    }

    public void init(UniverseCoreV2 core, CounterModelManager manager){
        Bukkit.getPluginManager().registerEvents(new LoginEvent(manager), core);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(manager), core);

        Bukkit.getPluginManager().registerEvents(new KillDeathCountEvents(manager), core);
        Bukkit.getPluginManager().registerEvents(new LifeCountEvents(manager), core);
        Bukkit.getPluginManager().registerEvents(new OreCountEvents(manager), core);
        Bukkit.getPluginManager().registerEvents(new PlayerCountEvents(manager), core);
    }
}
