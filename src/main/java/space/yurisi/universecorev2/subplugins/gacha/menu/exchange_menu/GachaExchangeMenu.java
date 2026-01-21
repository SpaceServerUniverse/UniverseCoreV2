package space.yurisi.universecorev2.subplugins.gacha.menu.exchange_menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.gacha.constrants.GachaType;
import space.yurisi.universecorev2.subplugins.gacha.menu.exchange_menu.item.GachaExchangeMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;
import java.util.stream.Collectors;

public class GachaExchangeMenu{

    public void sendMenu(Player player, GachaType gachaType){

        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        List<Item> items = gachaType.getURItems().stream()
                .map(itemID -> new GachaExchangeMenuItem(itemID, gachaType))
                .collect(Collectors.toList());

        Gui gui = PagedGui.items()
                .setStructure(
                        "# # # # # # # # #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# # # # # # # # #")
                .addIngredient('#', border)
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(items)
                .build();

        xyz.xenondevs.invui.window.Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle(gachaType.getTypeName())
                .build();

        window.open();
    }
}
