package space.yurisi.universecorev2.subplugins.levelaward;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.levelaward.event.UpEvent;

public class LevelAward implements SubPlugin {


    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getPluginManager().registerEvents(new UpEvent(), core);
    }


    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "levelaward";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}