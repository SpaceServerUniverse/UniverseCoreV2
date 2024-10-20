package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar_menu.BirthdayCalendarMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.time.MonthDay;
import java.util.List;

public class BirthdayCalendarMenuItem extends AbstractItem {
    List<BirthdayData> birthdayDatas;
    MonthDay monthDay;

    public BirthdayCalendarMenuItem(List<BirthdayData> birthdayDatas, MonthDay monthDay) {
        this.birthdayDatas = birthdayDatas;
        this.monthDay = monthDay;
    }

    @Override
    public ItemProvider getItemProvider() {
        if (!birthdayDatas.isEmpty()) {
            return new ItemBuilder(Material.GOLD_BLOCK)
                    .setDisplayName(monthDay.getDayOfMonth() + "日 - " + birthdayDatas.size() + "人の誕生日");
        }
        return new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(monthDay.getDayOfMonth() + "日");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        BirthdayCalendarMenu birthdayCalendarMenu = new BirthdayCalendarMenu(monthDay, birthdayDatas);
        birthdayCalendarMenu.sendMenu(player);
    }
}
