package space.yurisi.universecorev2.subplugins.chestshop.event;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.chestshop.event.block.BreakEvent;
import space.yurisi.universecorev2.subplugins.chestshop.event.block.SignChange;
import space.yurisi.universecorev2.subplugins.chestshop.event.player.InteractEvent;

public class EventManager {
    public EventManager(UniverseCoreV2 core){
        init(core);
    }
    private void init(UniverseCoreV2 core){
        Bukkit.getPluginManager().registerEvents(new BreakEvent(),core);
        Bukkit.getPluginManager().registerEvents(new SignChange(),core);
        Bukkit.getPluginManager().registerEvents(new InteractEvent(),core);
    }
}
