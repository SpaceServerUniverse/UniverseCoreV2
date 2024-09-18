package space.yurisi.universecorev2.subplugins.customname;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.customname.command.tagCommand;
import space.yurisi.universecorev2.subplugins.customname.event.JoinEvent;
import space.yurisi.universecorev2.subplugins.customname.event.UpEvent;

public class CustomName implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("tag").setExecutor(new tagCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(), core);
        Bukkit.getServer().getPluginManager().registerEvents(new UpEvent(), core);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "CustomName";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
