package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.BirthdayCalendar;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class ShowBirthdayCardMenuItem extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.CLOCK).setDisplayName("カレンダー");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        BirthdayCalendar calendarMenu = new BirthdayCalendar(null, null);
        calendarMenu.sendMenu(player);
    }
}
