package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class VoidBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage {

    public VoidBasePlayerDeathEventMessage(Player player) {
        super(player);
    }

    @Override
    protected void init(Player player) {
        setMessages(new Component[]{
                        Component.text("§a§l[戦闘型AI] §c" + player.getName() + "§a は消滅した"),
                        Component.text("§a§l[戦闘型AI] §c" + player.getName() + "§a は存在がなくなった"),
                        Component.text("§a§l[戦闘型AI] §c" + player.getName() + "§a はちりになった"),
                        Component.text("§a§l[戦闘型AI] §c" + player.getName() + "§a は星になった"),
                }
        );
    }
}
