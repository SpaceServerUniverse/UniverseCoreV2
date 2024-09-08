package space.yurisi.universecorev2.subplugins.salute;

import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.universecorev2.subplugins.salute.command.*;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;

import java.util.Objects;

public final class Salute implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("k").setExecutor(new kCommand());
        core.getCommand("oti").setExecutor(new otiCommand());
        core.getCommand("otu").setExecutor(new otuCommand());
    }

    @Override
    public void onDisable() {
    }

    @Override
    public String getName() {
        return "Salute";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
