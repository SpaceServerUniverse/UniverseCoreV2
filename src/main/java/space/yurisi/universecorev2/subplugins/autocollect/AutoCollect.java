package space.yurisi.universecorev2.subplugins.autocollect;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.autocollect.command.AutoCollectCommand;
import space.yurisi.universecorev2.subplugins.autocollect.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class AutoCollect implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getPluginManager().registerEvents(new Listener(), core);
        core.getCommand("autocollect").setExecutor(new AutoCollectCommand());
        List<String> aliases = new ArrayList<>();
        aliases.add("ac");
        core.getCommand("autocollect").setAliases(aliases);
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
