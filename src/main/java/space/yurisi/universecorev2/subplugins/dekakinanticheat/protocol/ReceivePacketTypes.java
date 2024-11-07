package space.yurisi.universecorev2.subplugins.dekakinanticheat.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketTypeEnum;

public class ReceivePacketTypes extends PacketTypeEnum {

    public static final ReceivePacketTypes INSTANCE;

    public static final PacketType ABILITY;

    static{
        ABILITY = PacketType.Play.Client.ABILITIES;
        INSTANCE = new ReceivePacketTypes();
    }
}
