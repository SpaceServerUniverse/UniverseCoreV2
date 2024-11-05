package space.yurisi.universecorev2.subplugins.dekakinanticheat;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.dekakinanticheat.protocol.PacketAnalyzer;

public class DAC implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        new PacketAnalyzer(core);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "DekakinAntiCheat";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
