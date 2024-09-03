package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class LavaBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage {
    public LavaBasePlayerDeathEventMessage(Player player) {
        super(player);
    }

    @Override
    protected void init(Player player) {
        setMessages(
                new Component[]{
                        Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a は溶岩の中を泳ごうとした"),
                        Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a は溶けた"),
                        Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a はどろどろになりました"),
                        Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a は存在がなくなりました")
                }
        );
    }
}
