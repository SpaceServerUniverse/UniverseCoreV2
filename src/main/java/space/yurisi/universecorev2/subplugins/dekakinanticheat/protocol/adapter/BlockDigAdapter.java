package space.yurisi.universecorev2.subplugins.dekakinanticheat.protocol.adapter;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;

public class BlockDigAdapter extends PacketAdapter {

    public BlockDigAdapter(@NotNull UniverseCoreV2 core) {
        super(core, PacketType.Play.Client.BLOCK_DIG);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        try{
            Bukkit.getLogger().info(event.getPacketType().toString());

            super.onPacketReceiving(event);
        }catch (IllegalStateException e) {
            return;
        }
    }
}
