package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class BlockExplosionBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage {
    public BlockExplosionBasePlayerDeathEventMessage(Player player) {
        super(player);
    }

    @Override
    protected void init(Player player) {
        setMessages(new Component[]{
                Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a は爆発した"),
                Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a は爆発の反動でばらばらになった"),
                Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a は爆発により生命活動が停止した"),
                Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a は爆発により骨になった")
        });
    }
}
