package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item.BirthdayBackItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item.BirthdayCalendarMenuItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar.item.BirthdayForwardItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BirthdayCalendar implements BaseMenu {
    private final BirthdayCardRepository birthdayCardRepository;
    private final int page;
    String charPools = "ABCDEFGHIJKLMNOPQRSTUVWXYZ012345678";
    private List<List<Item>> listItems;
    String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    private List<Item> currentItems;

    public BirthdayCalendar(List<List<Item>> listItems, Integer page) {
        birthdayCardRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getBirthdayCardRepository();
        this.listItems = listItems;
        this.page = Objects.requireNonNullElse(page, 0);
    }

    public void sendMenu(Player player) {
        if (listItems == null) {
            listItems = new ArrayList<>();
            currentItems = new ArrayList<>();
            for (int month = 1; month <= 12; month++) {
                List<BirthdayData> monthBirthdayData = birthdayCardRepository.getBirthdayDataByMonth(Month.of(month));
                Map<Integer, List<BirthdayData>> birthdayDataCache = monthBirthdayData.stream().collect(Collectors.groupingBy(BirthdayData::getDay));
                YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
                LocalDate firstDayOfMonth = yearMonth.atDay(1);
                int daysInMonth = yearMonth.lengthOfMonth();
                DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();
                int valueOfFirstDayOfMonth = firstDayOfWeek.getValue();
                for (int empty = 1; empty < valueOfFirstDayOfMonth; empty++) {
                    currentItems.add((new SimpleItem(new ItemBuilder(Material.AIR))));
                }
                for (int day = 1; day <= daysInMonth; day++) {
                    List<BirthdayData> birthdays = birthdayDataCache.getOrDefault(day, Collections.emptyList());
                    currentItems.add(new BirthdayCalendarMenuItem(birthdays, MonthDay.of(month, day)));
                }
                listItems.add(currentItems);
                currentItems = new ArrayList<>();
            }
        }


        this.currentItems = listItems.get(page);
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
                        "# H I J K L M N >",
                        "# O P Q R S T U <",
                        "# V W X Y Z 0 1 #",
                        "# 2 3 4 5 6 7 8 #")

                .addIngredient('#', border)
                .addIngredient('o', orange)
                .addIngredient('r', red)
                .addIngredient('l', lightblue)
                .addIngredient('g', green)
                .addIngredient('b', blue)
                .addIngredient('p', purple)
                .addIngredient('y', yellow)
                .addIngredient('>', new BirthdayForwardItem(listItems, page))
                .addIngredient('<', new BirthdayBackItem(listItems, page));
        for (int i = 0; i < charPools.length() && i < currentItems.size(); i++) {
            char c = charPools.charAt(i);
            guiNormal.addIngredient(c, currentItems.get(i));
        }
        Gui gui = guiNormal.build();
        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("BirthdayCard " + LocalDate.now().getYear() + " " + "(" + months[page] + ")")
                .build();
        window.open();
    }
}
