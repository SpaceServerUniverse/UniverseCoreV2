package space.yurisi.universecorev2.subplugins.levelsystem.manager;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.levelsystem.listener.block.BreakEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.listener.block.PlaceEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.listener.entity.DeathEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.listener.inventory.FurnaceExtractEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.listener.player.*;
import space.yurisi.universecorev2.subplugins.levelsystem.listener.plugin_event.level.UpEvent;

import java.util.logging.Level;

public class EventManager {

    public EventManager(UniverseCoreV2 core, LevelSystem levelSystem) {
        Bukkit.getServer().getPluginManager().registerEvents(new BreakEvent(), core);
        Bukkit.getServer().getPluginManager().registerEvents(new PlaceEvent(), core);

        Bukkit.getServer().getPluginManager().registerEvents(new DeathEvent(), core);

        Bukkit.getServer().getPluginManager().registerEvents(new FurnaceExtractEvent(), core);

        Bukkit.getServer().getPluginManager().registerEvents(new FishEvent(), core);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemConsumeEvent(), core);
        Bukkit.getServer().getPluginManager().registerEvents(new LoginEvent(levelSystem), core);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(levelSystem), core);
        Bukkit.getServer().getPluginManager().registerEvents(new QuitEvent(levelSystem), core);

        Bukkit.getServer().getPluginManager().registerEvents(new UpEvent(), core);
    }
}
