package space.yurisi.universecorev2.subplugins.birthdaycard.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.item.BackItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.item.CalenderItems;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.item.ForwardItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BirthdayCalendarMenu implements BaseMenu {

    public void sendMenu(Player player) {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        int firstDay = 1;

        calendar.set(year, month, firstDay);
        int firstDayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Item> items = new ArrayList<Item>();
        for (int i = 1; i < firstDayWeek; i++) {
            items.add(new SimpleItem(new ItemBuilder(Material.AIR).setDisplayName("")));
        }

        List<Item> items2 = new ArrayList<Item>();
        for (int i = firstDay; i <= lastDay; i++) {
            items2.add(new SimpleItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(i + "日")));
        }
        items.addAll(items2);

        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(""));
        Item orange = new SimpleItem(new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("月曜日"));
        Item red = new SimpleItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("火曜日"));
        Item lightblue = new SimpleItem(new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayName("水曜日"));
        Item green = new SimpleItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("木曜日"));
        Item blue = new SimpleItem(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName("金曜日"));
        Item purple = new SimpleItem(new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setDisplayName("土曜日"));
        Item yellow = new SimpleItem(new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName("日曜日"));
        //曜日のマス35
        Gui gui = PagedGui.items()
                .setStructure(
                        "# o r l g b p y #",
                        "# x x x x x x x #",
                        "# x x x x x x x >",
                        "# x x x x x x x <",
                        "# x x x x x x x #",
                        "# x x x x x x x #")

                .addIngredient('#', border)
                .addIngredient('o', orange)
                .addIngredient('r', red)
                .addIngredient('l', lightblue)
                .addIngredient('g', green)
                .addIngredient('b', blue)
                .addIngredient('p', purple)
                .addIngredient('y', yellow)
                .addIngredient('>', new ForwardItem())
                .addIngredient('<', new BackItem())
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(items)
                .build();
        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("BirthdayCard 2024")
                .build();
        window.open();
    }
}
