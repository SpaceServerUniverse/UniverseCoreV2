package space.yurisi.universecorev2.subplugins.changemessages.message.event.entity_damage_by_entity;


import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class PlayerDeathEntityByEntityEventMessage extends BaseDamageByEntityEventMessage {

    public PlayerDeathEntityByEntityEventMessage(Player player, Entity killer){
        super(player, killer);
    }

    @Override
    protected void init(Player player, Entity killer){
        setMessages(
                new Component[]{
                        Component.text("§a§l[死亡管理AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を殺害しました"),
                        Component.text("§a§l[死亡管理AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を骨にしました"),
                        Component.text("§a§l[死亡管理AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を倒しました"),
                        Component.text("§a§l[死亡管理AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a の存在をなくしました"),
                }
        );
    }

}
