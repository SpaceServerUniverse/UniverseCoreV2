package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar_menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar_menu.item.PlayerListBirthdayCardMenuItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar_menu.item.RegisterBirthdayCardMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.time.MonthDay;
import java.util.List;

public class BirthdayCalendarMenu implements BaseMenu {
    private MonthDay monthDay;
    private List<BirthdayData> birthdayDataList;

    public BirthdayCalendarMenu(MonthDay monthDay, List<BirthdayData> birthdayDataList) {
        this.monthDay = monthDay;
        this.birthdayDataList = birthdayDataList;
    }

    public void sendMenu(Player player) {
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(""));
        Gui gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # # ",
                        "# # r l . . . # # ",
                        "# # # # # # # # # ")
                .addIngredient('#', border)
                .addIngredient('r', new RegisterBirthdayCardMenuItem(monthDay))
                .addIngredient('l', new PlayerListBirthdayCardMenuItem(birthdayDataList))
                .build();
        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle(monthDay.getMonthValue() + "月 " + monthDay.getDayOfMonth() + "日")
                .build();
        window.open();
    }
}
