package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class EntityExplosionBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage{

    public EntityExplosionBasePlayerDeathEventMessage(Player player){
        super(player);
    }

    @Override
    protected void init(Player player) {
        setMessages(
                new Component[]{
                        Component.text("")
                }
        );
    }
}
