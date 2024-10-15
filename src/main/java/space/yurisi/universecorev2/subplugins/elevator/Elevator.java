package space.yurisi.universecorev2.subplugins.elevator;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.customname.command.tagCommand;
import space.yurisi.universecorev2.subplugins.customname.event.JoinEvent;
import space.yurisi.universecorev2.subplugins.customname.event.UpEvent;
import space.yurisi.universecorev2.subplugins.elevator.event.JumpEvent;
import space.yurisi.universecorev2.subplugins.elevator.event.SneakEvent;

public class Elevator implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getServer().getPluginManager().registerEvents(new JumpEvent(), core);
        Bukkit.getServer().getPluginManager().registerEvents(new SneakEvent(), core);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "Elevator";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
