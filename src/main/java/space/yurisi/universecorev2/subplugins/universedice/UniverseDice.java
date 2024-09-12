package space.yurisi.universecorev2.subplugins.universedice;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universedice.command.UniverseDiceCommand;

public class UniverseDice implements SubPlugin {

    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("dice").setExecutor(new UniverseDiceCommand());
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getName() {
        return "UniverseDice";
    }

    public String getVersion() {
        return "1.0.0";
    }
}
