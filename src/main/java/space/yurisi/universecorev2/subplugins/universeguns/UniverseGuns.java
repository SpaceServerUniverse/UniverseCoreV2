package space.yurisi.universecorev2.subplugins.universeguns;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universeguns.event.GunEvent;

public class UniverseGuns implements SubPlugin {

    public void onEnable(UniverseCoreV2 core) {
        // Plugin startup logic
        core.getServer().getPluginManager().registerEvents(new GunEvent(core), core);
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "UniverseGuns";
    }

    @Override
    public String getVersion() {
        return "1.0.1";
    }
}