package space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp;

import org.bukkit.*;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp.item.ListMywarpMenuItem;
import space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp.item.AddMywarpMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class MywarpInventoryMenu implements BaseMenu {

    private UniverseCoreAPIConnector connector;

    public MywarpInventoryMenu(UniverseCoreAPIConnector connector){
        this.connector = connector;
    }

    public void sendMenu(Player player){
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        Gui gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # #",
                        "# a b . . . . . #",
                        "# # # # # # # # #",
                        "# # # # # # # # #")
                .addIngredient('#', border)
                .addIngredient('a', new AddMywarpMenuItem())
                .addIngredient('b', new ListMywarpMenuItem(this.connector))
                .build();

        xyz.xenondevs.invui.window.Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("Mywarp")
                .build();

        window.open();
    }

}
