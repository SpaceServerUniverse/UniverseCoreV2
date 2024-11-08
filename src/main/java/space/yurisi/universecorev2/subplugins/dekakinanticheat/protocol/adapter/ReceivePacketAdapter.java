package space.yurisi.universecorev2.subplugins.dekakinanticheat.protocol.adapter;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.dekakinanticheat.protocol.ReceivePacketTypes;

public class ReceivePacketAdapter extends PacketAdapter {

    public ReceivePacketAdapter(@NotNull UniverseCoreV2 core) {
        super(core, new ReceivePacketTypes());
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        analyzePacket(event.getPacket(), event.getPlayer());
    }

    public void analyzePacket(PacketContainer container, Player player){
        if(container.getHandle() instanceof ServerboundPlayerAbilitiesPacket pk){

        }
    }
}
