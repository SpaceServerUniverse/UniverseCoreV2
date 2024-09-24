package space.yurisi.universecorev2.subplugins.playerhead;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.playerhead.command.PlayerHeadMenuCommand;

public class PlayerHead implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("head").setExecutor(new PlayerHeadMenuCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "PlayerHead";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
