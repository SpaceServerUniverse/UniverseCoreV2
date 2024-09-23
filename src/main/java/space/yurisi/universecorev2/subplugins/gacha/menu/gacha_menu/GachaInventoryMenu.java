package space.yurisi.universecorev2.subplugins.gacha.menu.gacha_menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.gacha.menu.gacha_menu.item.NormalGachaMenuItem;
import space.yurisi.universecorev2.subplugins.gacha.menu.gacha_menu.item.SolarSystemGachaDetailMenuItem;
import space.yurisi.universecorev2.subplugins.gacha.menu.gacha_menu.item.SolarSystemGachaMenuItem;
import space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp_menu.item.*;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class GachaInventoryMenu implements BaseMenu {

    public void sendMenu(Player player){

        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        Gui gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # #",
                        "# a b # # # # # #",
                        "# # c # # # # # #",
                        "# # # # # # # # #")
                .addIngredient('#', border)
                .addIngredient('a', new NormalGachaMenuItem())
                .addIngredient('b', new SolarSystemGachaMenuItem())
                .addIngredient('c', new SolarSystemGachaDetailMenuItem())
                .build();

        xyz.xenondevs.invui.window.Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("ガチャ")
                .build();

        window.open();
    }
}
