package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.changemessages.message.event.BaseEventMessage;

import java.util.Objects;
import java.util.Random;

public class BasePlayerDeathEventMessage extends BaseEventMessage {
    public BasePlayerDeathEventMessage(Player player){
        init(player);
    }

    public BasePlayerDeathEventMessage(Player player, Player killer){
        init(player, killer);
    }

    private boolean isNullItem(ItemStack item){
        if(item == null) return true;
        if(item.getType().name().equals("AIR")) return true;
        return false;
    }


    protected String getItemName(Player killer) {
        ItemStack hand_item = killer.getInventory().getItemInMainHand();
        if(isNullItem(hand_item)) {
            return "素手";
        }
        if (hand_item.getItemMeta().hasDisplayName()) {
            Component displayName = Objects.requireNonNull(hand_item.getItemMeta().displayName());
            return PlainTextComponentSerializer.plainText().serialize(displayName);
        }
        return hand_item.getType().name().replace("_", " ").toLowerCase();
    }



    protected void init(Player player){}

    protected void init(Player player, Player killer){}


}
