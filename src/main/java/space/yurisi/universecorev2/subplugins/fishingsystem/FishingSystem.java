package space.yurisi.universecorev2.subplugins.fishingsystem;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.fishingsystem.event.FishEvent;

public final class FishingSystem implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getServer().getPluginManager().registerEvents(new FishEvent(), core);
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "FishingSystem";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
