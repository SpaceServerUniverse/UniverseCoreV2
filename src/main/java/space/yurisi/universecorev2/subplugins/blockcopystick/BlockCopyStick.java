package space.yurisi.universecorev2.subplugins.blockcopystick;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.blockcopystick.event.InteractEvent;

public class BlockCopyStick implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getPluginManager().registerEvents(new InteractEvent(), core);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "BlockCopyStick";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
