package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.time.MonthDay;

public class RegisterBirthdayCardMenuItem extends AbstractItem {
    private final MonthDay monthDay;

    public RegisterBirthdayCardMenuItem(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAME_TAG).setDisplayName("登録");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.performCommand("birthday register " + monthDay.getMonthValue() + " " + monthDay.getDayOfMonth());
        player.closeInventory();
    }
}
