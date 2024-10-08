package space.yurisi.universecorev2.subplugins.freemarket.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Market;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.freemarket.menu.item.DownItem;
import space.yurisi.universecorev2.subplugins.freemarket.menu.item.MarketItem;
import space.yurisi.universecorev2.subplugins.freemarket.menu.item.UpItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.Collections;
import java.util.List;

public class MarketMenu implements BaseMenu {

    @Override
    public void sendMenu(Player player) {
        List<Item> items = new java.util.ArrayList<>(List.of());

        List<Market> markets = UniverseCoreV2API.getInstance().getDatabaseManager().getMarketRepository().getItems();
        for (int index = markets.size() - 1; index >= 0; index--){
            items.add(new MarketItem(markets.get(index)));
        }

        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        Gui gui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x #",
                        "x x x x x x x x u",
                        "x x x x x x x x d",
                        "x x x x x x x x #")
                .addIngredient('#', border)
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('u', new UpItem())
                .addIngredient('d', new DownItem())
                .setContent(items)
                .build();

        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("Market")
                .build();

        window.open();
    }
}
