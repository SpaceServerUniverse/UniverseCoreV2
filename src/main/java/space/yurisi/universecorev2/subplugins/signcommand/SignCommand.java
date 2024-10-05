package space.yurisi.universecorev2.subplugins.signcommand;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.signcommand.event.InteractEvent;


public final class SignCommand implements SubPlugin {


    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getPluginManager().registerEvents(new InteractEvent(), core);
    }


    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "signcommand";
    }

    @Override
    public String getVersion() {
        return "1.0.1";
    }
}