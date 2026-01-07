package space.yurisi.universecorev2.subplugins.cooking;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.cooking.event.CookingEventListener;

public class Cooking implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getServer().getPluginManager().registerEvents(new CookingEventListener(), core);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "Cooking";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
