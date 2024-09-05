package space.yurisi.universecorev2.subplugins.mywarp.menu.MywarpMenu;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.mywarp.menu.MywarpMenu.MenuItem.AddMywarpMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class MywarpInventoryMenu implements BaseMenu {

    public void sendMenu(Player player){
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        Gui gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # #",
                        "# a . . . . . . #",
                        "# # # # # # # # #",
                        "# # # # # # # # #")
                .addIngredient('#', border)
                .addIngredient('a', new AddMywarpMenuItem())
                .build();

        xyz.xenondevs.invui.window.Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("Mywarp")
                .build();

        window.open();
    }

}
