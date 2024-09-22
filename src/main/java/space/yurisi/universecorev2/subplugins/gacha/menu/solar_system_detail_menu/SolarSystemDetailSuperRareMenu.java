package space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;
import space.yurisi.universecorev2.item.LevellingCustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.solar_system.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SolarSystemDetailSuperRareMenu implements BaseMenu {

    public void sendMenu(Player player){
        List<ItemStack> items = new ArrayList<>();
        try {
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemSword.id)).getItem(1));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemSword.id)).getItem(2));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemSword.id)).getItem(3));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemSword.id)).getItem(4));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemSword.id)).getItem(5));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemPickaxe.id)).getItem(1));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemPickaxe.id)).getItem(2));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemPickaxe.id)).getItem(3));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemPickaxe.id)).getItem(4));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemPickaxe.id)).getItem(5));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemShovel.id)).getItem(1));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemShovel.id)).getItem(2));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemShovel.id)).getItem(3));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemShovel.id)).getItem(4));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemShovel.id)).getItem(5));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemAxe.id)).getItem(1));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemAxe.id)).getItem(2));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemAxe.id)).getItem(3));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemAxe.id)).getItem(4));
            items.add(((LevellingCustomItem) UniverseItem.getItem(SolarSystemAxe.id)).getItem(5));
        }catch(CustomItemLevelNotFoundException ignored){

        }

        List<Item> gachaItems = items.stream()
                .map(GachaDetailItem::new)
                .collect(Collectors.toList());


        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        Gui gui = PagedGui.items()
                .setStructure(
                        "x x x x x # # # #",
                        "x x x x x # # # #",
                        "x x x x x # # # #",
                        "n r u b t f # # #")
                .addIngredient('#', border)
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('t', new TopItem())
                .addIngredient('n', new NormalItemMenuItem())
                .addIngredient('r', new RareItemMenuItem())
                .addIngredient('u', new UltraRareItemMenuItem())
                .addIngredient('b', new BackItem())
                .addIngredient('f', new ForwardItem())
                .setContent(gachaItems)
                .build();

        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("SolarSystem SR")
                .build();

        window.open();
    }
}
