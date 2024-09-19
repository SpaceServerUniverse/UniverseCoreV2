package space.yurisi.universecorev2.subplugins.damagemanager;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.damagemanager.event.DamageByEntityEvent;
import space.yurisi.universecorev2.subplugins.damagemanager.utils.Config;

public class DamageManager implements SubPlugin {

    private Config config;

    @Override
    public void onEnable(UniverseCoreV2 core) {
        this.config = new Config(core);
        Bukkit.getServer().getPluginManager().registerEvents(new DamageByEntityEvent(config), core);
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "DamageManager";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}