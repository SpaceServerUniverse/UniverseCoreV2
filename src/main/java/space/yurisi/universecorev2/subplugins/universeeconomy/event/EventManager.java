package space.yurisi.universecorev2.subplugins.universeeconomy.event;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomy;
import space.yurisi.universecorev2.subplugins.universeeconomy.event.player.LoginEvent;
import space.yurisi.universecorev2.subplugins.universeeconomy.event.player.QuitEvent;

public class EventManager {

    public EventManager(UniverseCoreV2 core, UniverseEconomy universeEconomy){
        init(core, universeEconomy);
    }

    private void init(UniverseCoreV2 core, UniverseEconomy universeEconomy){
        Bukkit.getPluginManager().registerEvents(new LoginEvent(universeEconomy.getDatabaseManager()), core);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), core);
    }
}
