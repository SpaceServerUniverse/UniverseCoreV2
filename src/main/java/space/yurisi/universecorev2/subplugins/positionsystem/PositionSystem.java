package space.yurisi.universecorev2.subplugins.positionsystem;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.positionsystem.command.gmCommand;

import java.util.Objects;

public final class PositionSystem implements SubPlugin {

    public void onEnable(UniverseCoreV2 core) {
        Objects.requireNonNull(core.getCommand("gm")).setExecutor(new gmCommand());
    }

    public void onDisable() {
    }

    @Override
    public String getName() {
        return "PositionSystem";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
