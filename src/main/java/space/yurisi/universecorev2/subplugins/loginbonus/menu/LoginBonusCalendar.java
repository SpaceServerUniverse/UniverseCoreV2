package space.yurisi.universecorev2.subplugins.loginbonus.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.database.repositories.LoginBonusRepository;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item.BirthdayBackItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item.BirthdayCalendarMenuItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item.BirthdayForwardItem;
import space.yurisi.universecorev2.subplugins.loginbonus.menu.item.LoginBonusCalendarMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class LoginBonusCalendar implements BaseMenu {

    private LoginBonusRepository loginBonusRepository;

    String charPools = "ABCDEFGHIJKLMNOPQRSTUVWXYZ012345678";

    private List<Item> currentItems;

    public LoginBonusCalendar() {
        loginBonusRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getLoginBonusRepository();
    }

    public void sendMenu(Player player) {
        currentItems = new ArrayList<>();
        Month month = LocalDate.now().getMonth();
        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();
        DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();
        int valueOfFirstDayOfMonth = firstDayOfWeek.getValue();
        for (int empty = 1; empty < valueOfFirstDayOfMonth; empty++) {
            currentItems.add((new SimpleItem(new ItemBuilder(Material.AIR))));
        }
        for (int day = 1; day <= daysInMonth; day++) {
            currentItems.add(new LoginBonusCalendarMenuItem(MonthDay.of(month, day)));
        }

        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(""));
        Item yellow = new SimpleItem(new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName("日曜日"));
        Item orange = new SimpleItem(new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("月曜日"));
        Item red = new SimpleItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("火曜日"));
        Item lightblue = new SimpleItem(new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayName("水曜日"));
        Item green = new SimpleItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("木曜日"));
        Item blue = new SimpleItem(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName("金曜日"));
        Item purple = new SimpleItem(new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setDisplayName("土曜日"));
        Gui.Builder.Normal guiNormal = Gui.normal()
                .setStructure(
                        "# o r l g b p y #",
                        "# A B C D E F G #",
                        "# H I J K L M N #",
                        "# O P Q R S T U #",
                        "# V W X Y Z 0 1 #",
                        "# 2 3 4 5 6 7 8 #")

                .addIngredient('#', border)
                .addIngredient('o', orange)
                .addIngredient('r', red)
                .addIngredient('l', lightblue)
                .addIngredient('g', green)
                .addIngredient('b', blue)
                .addIngredient('p', purple)
                .addIngredient('y', yellow);
        for (int i = 0; i < charPools.length() && i < currentItems.size(); i++) {
            char c = charPools.charAt(i);
            guiNormal.addIngredient(c, currentItems.get(i));
        }
        Gui gui = guiNormal.build();
        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("ログインボーナス " + LocalDate.now().getYear() + " " + "(" + LocalDate.now().getMonth().getValue() + "月)")
                .build();
        window.open();
    }
}
