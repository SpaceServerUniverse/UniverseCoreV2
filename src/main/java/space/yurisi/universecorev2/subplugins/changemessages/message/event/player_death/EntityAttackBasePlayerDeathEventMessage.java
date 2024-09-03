package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class EntityAttackBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage {

    public EntityAttackBasePlayerDeathEventMessage(Player player, Player killer) {
        super(player, killer);
    }

    @Override
    protected void init(Player player, Player killer) {
        if(killer == null){
            setMessages(
                    new Component[]{
                            Component.text("")
                    }
            );
            return;
        }

        if (player.getName().equals(killer.getName())) {
            setMessages(
                    new Component[]{
                            Component.text("§a§l[死亡管理AI] §c" + player.getName() + "§a が自爆した")
                    }
            );
            return;
        }

        String item = getItemName(killer);

        setMessages(
                new Component[]{
                        Component.text("§a§l[戦闘型AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- で殺害しました"),
                        Component.text("§a§l[戦闘型AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- で刺し殺しました"),
                        Component.text("§a§l[戦闘型AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- で殴り倒しました"),
                        Component.text("§a§l[戦闘型AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- でばらばらにしました"),
                }
        );
    }
}
