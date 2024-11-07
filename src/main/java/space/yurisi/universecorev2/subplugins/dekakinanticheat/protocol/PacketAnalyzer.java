package space.yurisi.universecorev2.subplugins.dekakinanticheat.protocol;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.dekakinanticheat.protocol.adapter.ReceivePacketAdapter;

public class PacketAnalyzer{

    public PacketAnalyzer(UniverseCoreV2 core) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new ReceivePacketAdapter(core));
    }
}
