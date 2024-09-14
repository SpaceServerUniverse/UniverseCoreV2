package space.yurisi.universecorev2.subplugins.freemarket;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.freemarket.command.marketCommand;

public final class FreeMarket implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("market").setExecutor(new marketCommand());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "FreeMarket";
    }

    @Override
    public String getVersion() {
        return "1.0.0-alpha";
    }
}
