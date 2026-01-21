package space.yurisi.universecorev2.subplugins.gacha.core.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.LevellingCustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.solar_system.*;
import space.yurisi.universecorev2.item.ticket.LoseTicket;
import space.yurisi.universecorev2.subplugins.gacha.constrants.GachaType;

import java.util.Objects;

public class SolarSystemEventGacha extends EventGacha {

    public SolarSystemEventGacha(Player player) {
        super(player);
    }

    @Override
    protected void initializeNormalList() {
        LoseTicket ticket = (LoseTicket) Objects.requireNonNull(UniverseItem.getItem(LoseTicket.id));
        ItemStack itemStack = ticket.getItem();
        ticket.setLoseTicketType(itemStack, GachaType.SOLAR_SYSTEM);
        normal.add(itemStack);
    }

    @Override
    protected void initializeRareList() {
        LoseTicket ticket = (LoseTicket) Objects.requireNonNull(UniverseItem.getItem(LoseTicket.id));
        ItemStack itemStack = ticket.getItem();
        itemStack.setAmount(5);
        ticket.setLoseTicketType(itemStack, GachaType.SOLAR_SYSTEM);
        rare.add(itemStack);
    }

    @Override
    protected void initializeSuperRareList() {
        for(String id : GachaType.SOLAR_SYSTEM.getSRItems()) {
            try{
                super_rare.add(((LevellingCustomItem) Objects.requireNonNull(UniverseItem.getItem(id))).getItem(1));
            } catch (CustomItemLevelNotFoundException ignored) {
                super_rare.add(getErrorItem());
            }
        }
    }

    @Override
    protected void initializeUltraRareList() {
        for(String id : GachaType.SOLAR_SYSTEM.getURItems()){
            ultra_rare.add(UniverseItem.getItem(id).getItem());
        }
    }
}
