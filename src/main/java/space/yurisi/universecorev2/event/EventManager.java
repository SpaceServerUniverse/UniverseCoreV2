package space.yurisi.universecorev2.event;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.event.block.ExplosionPrimeEvent;
import space.yurisi.universecorev2.event.player.LoginEvent;

public class EventManager {
    public EventManager(UniverseCoreV2 main){
        init(main);
    }

    private void init(UniverseCoreV2 main) {
        Bukkit.getPluginManager().registerEvents(new LoginEvent(), main);
        Bukkit.getPluginManager().registerEvents(new ExplosionPrimeEvent(),main);
    }
}
