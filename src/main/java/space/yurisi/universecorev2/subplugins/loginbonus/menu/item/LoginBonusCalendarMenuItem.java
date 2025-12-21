package space.yurisi.universecorev2.subplugins.loginbonus.menu.item;

import org.apache.commons.lang3.time.DateUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.models.LoginBonus;
import space.yurisi.universecorev2.database.repositories.LoginBonusRepository;
import space.yurisi.universecorev2.exception.LoginBonusNotFoundException;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar_menu.BirthdayCalendarMenu;
import space.yurisi.universecorev2.subplugins.loginbonus.menu.LoginBonusCalendar;
import space.yurisi.universecorev2.subplugins.loginbonus.utils.LoginBonusType;
import space.yurisi.universecorev2.utils.Message;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoginBonusCalendarMenuItem extends AbstractItem {
    private final Calendar calendar;
    private final boolean received;
    private final LoginBonusType loginBonusType = new LoginBonusType();

    public LoginBonusCalendarMenuItem(Calendar calendar, boolean received) {
        this.calendar = calendar;
        this.received = received;
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemBuilder item = loginBonusType.getItemBuilderByCalendar(calendar);
        String name = loginBonusType.getLoginBonusNameByCalendar(calendar);

        if (received) {
            item.setMaterial(Material.RED_STAINED_GLASS_PANE)
                    .setLegacyLore(List.of("§e" + name, "§c受け取り済みです"));
            return item;
        }

        if (isToday()) {
            item.setMaterial(Material.GREEN_STAINED_GLASS_PANE)
                    .setLegacyLore(List.of("§e" + name, "§a▶ クリックして受け取る！"));
            return item;
        }

        item.setLegacyLore(List.of("§e" + name));
        return item;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType,
                            @NotNull Player player,
                            @NotNull InventoryClickEvent event) {

        if (!isToday()) return;

        if (received) {
            Message.sendErrorMessage(player, "[ログインAI]", "すでに受け取っています!");
            return;
        }

        player.performCommand("loginbonus receive");
        new LoginBonusCalendar().sendMenu(player);
    }

    private boolean isToday() {
        Date now = new Date();
        Date truncateNow = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        Date truncateDay = DateUtils.truncate(calendar.getTime(), Calendar.DAY_OF_MONTH);
        return truncateNow.equals(truncateDay);
    }
}