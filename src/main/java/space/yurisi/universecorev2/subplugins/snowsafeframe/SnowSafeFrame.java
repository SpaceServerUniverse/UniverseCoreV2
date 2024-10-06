package space.yurisi.universecorev2.subplugins.snowsafeframe;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.snowsafeframe.event.EventManager;

public class SnowSafeFrame implements SubPlugin {
    public void onEnable(UniverseCoreV2 core) {
        new EventManager(core);
    }

    public void onDisable() {

    }

    public String getName() {
        return "SnowSafeFrame";
    }

    public String getVersion() {
        return "1.0.0";
    }
}
