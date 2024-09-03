package space.yurisi.universecorev2.subplugins.changemessages.message.event.entity_damage_by_entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.changemessages.message.event.BaseEventMessage;

public class BaseDamageByEntityEventMessage extends BaseEventMessage {

    public BaseDamageByEntityEventMessage(Player player, Entity killer){
        init(player, killer);
    }

    protected void init(Player player, Entity killer){}
}
