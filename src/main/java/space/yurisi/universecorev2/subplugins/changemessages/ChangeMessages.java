package space.yurisi.universecorev2.subplugins.changemessages;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.changemessages.event.EventManager;
import space.yurisi.universecorev2.subplugins.changemessages.file.Config;
import space.yurisi.universecorev2.subplugins.SubPlugin;


public final class ChangeMessages implements SubPlugin {

    private Config config;

    public void onEnable(UniverseCoreV2 core) {
        this.config = new Config(core);
        new EventManager(core, this);
    }

    public Config getConfigClass(){
        return config;
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "ChangeMessages";
    }

    @Override
    public String getVersion() {
        return "1.2.3";
    }
}
