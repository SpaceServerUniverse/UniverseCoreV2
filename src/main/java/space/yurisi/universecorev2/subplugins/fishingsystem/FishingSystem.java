package space.yurisi.universecorev2.subplugins.fishingsystem;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.damagemanager.event.DamageByEntityEvent;
import space.yurisi.universecorev2.subplugins.fishingsystem.command.fishrodCommand;
import space.yurisi.universecorev2.subplugins.fishingsystem.event.FishEvent;
import space.yurisi.universecorev2.subplugins.repaircream.command.repairCommand;

public final class FishingSystem implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getServer().getPluginManager().registerEvents(new FishEvent(), core);
        core.getCommand("fishrod").setExecutor(new fishrodCommand());

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
