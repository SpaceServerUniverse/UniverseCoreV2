package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class FallBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage {
    public FallBasePlayerDeathEventMessage(Player player) {
        super(player);
    }

    @Override
    protected void init(Player player) {
        setMessages(
                new Component[]{
                        Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a は落下してぐちゃぐちゃになった"),
                        Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a は空中浮遊を試みたが落下しました"),
                        Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a は落下で心中を試みました"),
                        Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a は床が外れて落下しました"),
                }
        );
    }
}
