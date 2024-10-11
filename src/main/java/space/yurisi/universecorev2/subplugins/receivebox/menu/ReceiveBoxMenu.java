package space.yurisi.universecorev2.subplugins.receivebox.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.menu.menu_item.BackItem;
import space.yurisi.universecorev2.menu.menu_item.ForwardItem;
import space.yurisi.universecorev2.subplugins.receivebox.menu.menu_item.ReceiveBoxItem;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiveBoxMenu implements BaseMenu {

    @Override
    public void sendMenu(Player player) {
        List<Item> items = UniverseCoreV2API.getInstance().getDatabaseManager().getReceiveBoxRepository().getReceiveBoxesFromPlayer( player).stream()
                .map(ReceiveBoxItem::new)
                .collect(Collectors.toList());

        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        PagedGui<?> gui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x #",
                        "x x x x x x x x u",
                        "x x x x x x x x d",
                        "x x x x x x x x #")
                .addIngredient('#', border)
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('u', new BackItem())
                .addIngredient('d', new ForwardItem())
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
