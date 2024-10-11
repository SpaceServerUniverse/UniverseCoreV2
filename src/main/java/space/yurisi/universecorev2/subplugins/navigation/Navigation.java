package space.yurisi.universecorev2.subplugins.navigation;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.navigation.file.Config;

public class Navigation implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        Config config = new Config(core);
        core.getCommand("wiki").setExecutor(new NavigationCommand("wiki", config));
        core.getCommand("discord").setExecutor(new NavigationCommand("discord", config));
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(config), core);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "Navigation";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
