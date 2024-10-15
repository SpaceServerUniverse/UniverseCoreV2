package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.BirthdayCalendar;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.List;

public class BirthdayForwardItem extends AbstractItem {
    private List<List<Item>> listItems;
    private int page;

    public BirthdayForwardItem(List<List<Item>> listItems, int page) {
        this.page = page;
        this.listItems = listItems;
    }

    public boolean hasNextPage() {
        return this.page >= 0 && this.page < this.getPageAmount() - 1;
    }

    private int getPageAmount() {
        return this.listItems != null ? this.listItems.size() : 0;
    }

    public ItemProvider getItemProvider() {
        ItemBuilder builder = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("次のページ");
        builder.setDisplayName("次のページ")
                .addLoreLines(hasNextPage()
                        ? (page +2) + "/" + getPageAmount()
                        : "最後のページです");
        return builder;
    }

    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if (clickType == ClickType.LEFT) {
            if(hasNextPage()) {
                BirthdayCalendar birthdayCalendar = new BirthdayCalendar(listItems,page + 1);
                birthdayCalendar.sendMenu(player);
            }
        }
    }
}
