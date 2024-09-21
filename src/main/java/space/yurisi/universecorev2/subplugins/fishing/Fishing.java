package space.yurisi.universecorev2.subplugins.fishing;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;

public class Fishing implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getServer().getPluginManager().registerEvents(new EventListener(core.getLogger()), core);
    }

    @Override
    public void onDisable() {}

    @Override
    public String getName() {
        return "Fishing";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }


}
