package space.yurisi.universecorev2.subplugins.autocollect;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.autocollect.command.AutoCollectCommand;
import space.yurisi.universecorev2.subplugins.autocollect.data.AutoCollectMap;
import space.yurisi.universecorev2.subplugins.autocollect.event.AutoCollectListener;

public class AutoCollect implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        new AutoCollectMap();
        Bukkit.getPluginManager().registerEvents(new AutoCollectListener(), core);
        core.getCommand("autocollect").setExecutor(new AutoCollectCommand());
    }

    @Override
    public void onDisable() {
        //NOOP
    }

    @Override
    public String getName() {
        return "AutoCollect";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
