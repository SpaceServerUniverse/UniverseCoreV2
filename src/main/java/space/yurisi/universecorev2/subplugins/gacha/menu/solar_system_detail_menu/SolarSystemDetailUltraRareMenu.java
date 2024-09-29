package space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.menu.menu_item.BackItem;
import space.yurisi.universecorev2.menu.menu_item.ForwardItem;
import space.yurisi.universecorev2.subplugins.gacha.core.event.SolarSystemEventGacha;
import space.yurisi.universecorev2.subplugins.gacha.menu.menu_item.TopItem;
import space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.item.*;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;
import java.util.stream.Collectors;

public class SolarSystemDetailUltraRareMenu implements BaseMenu {

    public void sendMenu(Player player){
        List<Item> items = new SolarSystemEventGacha(player).getUltraRare().stream()
                .map(GachaDetailItem::new)
                .collect(Collectors.toList());

        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        Gui gui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "n r s # t # # # #")
                .addIngredient('#', border)
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('t', new TopItem())
                .addIngredient('n', new NormalItemMenuItem())
                .addIngredient('r', new RareItemMenuItem())
                .addIngredient('s', new SuperRareItemMenuItem())
                .setContent(items)
                .build();

        xyz.xenondevs.invui.window.Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("SolarSystem UR")
                .build();

        window.open();
    }
}
