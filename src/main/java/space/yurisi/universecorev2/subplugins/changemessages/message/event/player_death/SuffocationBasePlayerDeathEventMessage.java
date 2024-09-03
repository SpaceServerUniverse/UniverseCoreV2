package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class SuffocationBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage {
    public SuffocationBasePlayerDeathEventMessage(Player player) {
        super(player);
    }

    @Override
    protected void init(Player player) {
        setMessages(
                new Component[]{
                        Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a は窒息しました"),
                        Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a は押しつぶされました"),
                        Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a は押しつぶされてぐちゃぐちゃになりました"),
                        Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a は押しつぶされて息の根が止まりました")
                }
        );
    }
}
