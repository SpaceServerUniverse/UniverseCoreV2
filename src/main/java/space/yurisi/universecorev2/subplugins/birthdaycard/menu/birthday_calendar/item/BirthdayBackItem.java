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

public class BirthdayBackItem extends AbstractItem {

    private final List<List<Item>> listItems;
    private final int page;

    public BirthdayBackItem(List<List<Item>> listItems, int page) {
        this.page = page;
        this.listItems = listItems;
    }

    private boolean hasPreviousPage() {
        return page > 0;
    }

    private int getPageAmount() {
        return this.listItems != null ? this.listItems.size() : 0;
    }

    public ItemProvider getItemProvider() {
        ItemBuilder builder = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("前月");
        builder.setDisplayName("前月")
                .addLoreLines(hasPreviousPage()
                        ? page + "/" + getPageAmount()
                        : "最初の月です");
        return builder;
    }

    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if (clickType == ClickType.LEFT) {
            if (hasPreviousPage()) {
                BirthdayCalendar birthdayCalendar = new BirthdayCalendar(listItems, page - 1);
                birthdayCalendar.sendMenu(player);
            }
        }
    }
}
