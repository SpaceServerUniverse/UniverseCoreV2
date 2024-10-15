package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_player_list_menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_player_list_menu.item.PlayerHeadItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.item.BackItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.item.ForwardItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.item.TopItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BirthdayPlayerListMenu implements BaseMenu {
    private List<BirthdayData> birthdayDataList;

    public BirthdayPlayerListMenu(List<BirthdayData> birthdayDataList) {
        this.birthdayDataList = birthdayDataList;
    }

    public void sendMenu(Player player) {
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(""));
        List<Item> items = new ArrayList<>();
        birthdayDataList.forEach(birthdayData -> {
            items.add(new PlayerHeadItem(UUID.fromString(birthdayData.getUuid())));
        });
        Gui gui = PagedGui.items()
                .setStructure(
                        "# # # # # # # # #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# # # < t > # # #")
                .addIngredient('#', border)
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('t', new TopItem())
                .addIngredient('<', new BackItem())
                .addIngredient('>', new ForwardItem())
                .setContent(items)
                .build();
        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("PlayerList")
                .build();
        window.open();
    }
}
