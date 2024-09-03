package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class FireTickBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage {
    public FireTickBasePlayerDeathEventMessage(Player player) {
        super(player);
    }

    @Override
    protected void init(Player player) {
        setMessages(
                new Component[]{
                        Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a はケツに火がついた"),
                        Component.text("§a§l[死亡管理AI]§b " + player.getName() + "§a は火だるまになった")
                }
        );
    }
}
