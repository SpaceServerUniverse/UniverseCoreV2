package space.yurisi.universecorev2.subplugins.gacha.menu.exchange_menu.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.ticket.LoseTicket;
import space.yurisi.universecorev2.subplugins.gacha.constrants.GachaType;
import space.yurisi.universecorev2.utils.Message;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.Objects;

public class GachaExchangeMenuItem extends AbstractItem {

    private final String itemID;
    private ItemStack item;
    private final GachaType gachaType;

    public GachaExchangeMenuItem(String itemID, GachaType gachaType) {
        this.itemID = itemID;
        this.gachaType = gachaType;
    }

    @Override
    public ItemProvider getItemProvider(){
        item = UniverseItem.getItem(itemID).getItem();
        return new ItemBuilder(item);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        PlayerInventory inventory = player.getInventory();

        boolean removed = UniverseItem.removeItem(player, LoseTicket.id, 100);
        if(removed) {
            Message.sendSuccessMessage(player, "[ガチャAI]", "ハズレ券を使用して" + Objects.requireNonNull(UniverseItem.getItem(itemID)).getName() + "§fと交換しました！");
            inventory.addItem(item);
        }else{
            Message.sendWarningMessage(player, "[ガチャAI]",  gachaType.getTypeName() + "の§d§lハズレ券§fが不足しています。");
            return;
        }
        inventory.close();
    }
}
