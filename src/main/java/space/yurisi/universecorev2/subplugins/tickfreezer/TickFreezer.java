package space.yurisi.universecorev2.subplugins.tickfreezer;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.tickfreezer.event.EventManager;

public class TickFreezer implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        new EventManager(core);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "TickFreezer";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
