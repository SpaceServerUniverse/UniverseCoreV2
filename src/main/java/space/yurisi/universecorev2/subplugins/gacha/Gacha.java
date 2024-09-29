package space.yurisi.universecorev2.subplugins.gacha;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.gacha.command.gachaCommand;

public class Gacha implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("gacha").setExecutor(new gachaCommand());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "gacha";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
