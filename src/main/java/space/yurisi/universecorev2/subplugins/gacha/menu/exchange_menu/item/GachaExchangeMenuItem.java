package space.yurisi.universecorev2.subplugins.gacha.menu.exchange_menu.item;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.ticket.LoseTicket;
import space.yurisi.universecorev2.subplugins.gacha.constrants.GachaType;
import space.yurisi.universecorev2.utils.Message;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

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
        NamespacedKey loseTicketKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.LOSE_TICKET_TYPE);
        int totalTickets = 0;
        for(ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() == Material.PAPER) {
                ItemMeta meta = itemStack.getItemMeta();
                if (meta != null) {
                    String typeId = meta.getPersistentDataContainer().get(loseTicketKey, PersistentDataType.STRING);
                    if (typeId != null && typeId.equals(gachaType.getId())) {
                        totalTickets += itemStack.getAmount();
                    }
                }
            }
        }
        if (totalTickets < 100) {
            Message.sendWarningMessage(player, "[ガチャAI]",  gachaType.getTypeName() + "の§d§lハズレ券§fが不足しています。");
            return;
        }
        boolean removed = UniverseItem.removeItem(player, LoseTicket.id, 100);
        if(removed) {
            Message.sendSuccessMessage(player, "[ガチャAI]", "ハズレ券を使用して" + UniverseItem.getItem(itemID).getName() + "§fと交換しました！");
            inventory.addItem(item);
        }
        inventory.close();
    }
}
