package space.yurisi.universecorev2.event;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.event.block.BExplodeEvent;
import space.yurisi.universecorev2.event.entity.ChangeBlockEvent;
import space.yurisi.universecorev2.event.entity.EExplodeEvent;
import space.yurisi.universecorev2.event.entity.EntityHangingBreakEvent;
import space.yurisi.universecorev2.event.player.*;

public class EventManager {
    public EventManager(UniverseCoreV2 main){
        init(main);
    }

    private void init(UniverseCoreV2 main) {
        Bukkit.getPluginManager().registerEvents(new LoginEvent(), main);
        Bukkit.getPluginManager().registerEvents(new OpenMainMenuEvent(), main);
        Bukkit.getPluginManager().registerEvents(new BExplodeEvent(),main);
        Bukkit.getPluginManager().registerEvents(new EExplodeEvent(),main);
        Bukkit.getPluginManager().registerEvents(new BlockProtectEvent(), main);
        Bukkit.getPluginManager().registerEvents(new EntityHangingBreakEvent(),main);
        Bukkit.getPluginManager().registerEvents(new ChangeBlockEvent(),main);
    }
}
