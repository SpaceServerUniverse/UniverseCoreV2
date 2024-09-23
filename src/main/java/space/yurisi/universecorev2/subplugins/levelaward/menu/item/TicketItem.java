package space.yurisi.universecorev2.subplugins.levelaward.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.ticket.GachaTicket;
import space.yurisi.universecorev2.subplugins.levelaward.LevelAward;
import space.yurisi.universecorev2.subplugins.levelaward.menu.LevelMenu;
import space.yurisi.universecorev2.utils.Message;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.io.IOException;

public class TicketItem extends AbstractItem {

    private final int amount;
    private Player player;

    public TicketItem(Player player,int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public ItemProvider getItemProvider() {
        CustomItem Item = UniverseItem.getItem(GachaTicket.id);
        if (Item != null) {
            ItemStack ItemStack = Item.getItem();
            ItemBuilder itemBuilder = new ItemBuilder(ItemStack);
            itemBuilder.setAmount(this.amount);
            return itemBuilder;

        }
        return new ItemBuilder(new ItemStack(Material.AIR));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if (player.getInventory().firstEmpty() != -1){
            CustomItem customItem = UniverseItem.getItem(GachaTicket.id);
            if (customItem != null) {
                ItemStack itemStack = customItem.getItem();
                itemStack.setAmount(this.amount);
                player.getInventory().addItem(itemStack);
                try {
                    LevelAward.getInstance().getConfig().setTicket(player.getUniqueId().toString(),LevelAward.getInstance().getConfig().getTicket(player.getUniqueId().toString())-this.amount);
                } catch (IOException e) {
                    Message.sendErrorMessage(player, "[めぬ（笑）]", "うぇらー、吐いてますよー（笑）");
                }
            }
        }
        new LevelMenu().sendMenu(player);
    }
}
