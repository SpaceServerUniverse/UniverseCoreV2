package space.yurisi.universecorev2.subplugins.loginbonus.menu;

import org.apache.commons.lang3.time.DateUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.models.LoginBonus;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.database.repositories.LoginBonusRepository;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item.BirthdayBackItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item.BirthdayCalendarMenuItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item.BirthdayForwardItem;
import space.yurisi.universecorev2.subplugins.loginbonus.menu.item.LoginBonusCalendarMenuItem;
import space.yurisi.universecorev2.utils.DateHelper;
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

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue(); // 1-12
        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();

        // ★ 日曜始まり：日=0, 月=1, ... 土=6
        int startIndex = firstDayOfMonth.getDayOfWeek().getValue() % 7;

        // 月初までの空白
        for (int i = 0; i < startIndex; i++) {
            currentItems.add(new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)));
        }

        Date now = new Date();
        Date startDate = DateHelper.getFirstDate(now);
        Date endDate = DateHelper.getLastDate(now);
        List<LoginBonus> loginBonus = loginBonusRepository.getLoginBonusesByPlayerAndDateBetween(player, startDate, endDate);

        // 日付アイテム
        for (int day = 1; day <= daysInMonth; day++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1); // ★ Calendarは0始まり
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            boolean received = loginBonus.stream()
                    .anyMatch(bonus ->
                            Boolean.TRUE.equals(bonus.getIs_received())
                                    && DateUtils.isSameDay(bonus.getLast_login_date(), cal.getTime())
                    );
            currentItems.add(new LoginBonusCalendarMenuItem(cal, received));
        }

        // ★ 6週分（42枠）まで埋める
        while (currentItems.size() < 42) {
            currentItems.add(new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)));
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
                        "# y o r l g b p #",
                        "# A B C D E F G #",
                        "# H I J K L M N #",
                        "# O P Q R S T U #",
                        "# V W X Y Z 0 1 #",
                        "# 2 3 4 5 6 7 8 #")
                .addIngredient('#', border)
                .addIngredient('y', yellow)
                .addIngredient('o', orange)
                .addIngredient('r', red)
                .addIngredient('l', lightblue)
                .addIngredient('g', green)
                .addIngredient('b', blue)
                .addIngredient('p', purple);

        for (int i = 0; i < charPools.length() && i < currentItems.size(); i++) {
            guiNormal.addIngredient(charPools.charAt(i), currentItems.get(i));
        }

        Window.single()
                .setViewer(player)
                .setGui(guiNormal.build())
                .setTitle("ログインボーナス " + year + " (" + month + "月)")
                .build()
                .open();
    }

    // 月初日を返す
    private static Date getFirstDate(Date date) {

        if (date == null) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int first = calendar.getActualMinimum(Calendar.DATE);
        calendar.set(Calendar.DATE, first);

        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 000);

        return calendar.getTime();
    }

    // 月末日を返す
    private static Date getLastDate(Date date) {

        if (date == null) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int last = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, last);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }
}
