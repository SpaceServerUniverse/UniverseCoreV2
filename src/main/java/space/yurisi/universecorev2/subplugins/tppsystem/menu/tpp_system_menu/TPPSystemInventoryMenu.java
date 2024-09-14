package space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu;

import org.bukkit.*;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.tppsystem.TPPSystem;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.item.*;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class TPPSystemInventoryMenu implements BaseMenu {

    private final TPPSystem tppSystem;

    private final UniverseCoreAPIConnector connector;
    public TPPSystemInventoryMenu(TPPSystem tppSystem, UniverseCoreAPIConnector connector){
        this.tppSystem = tppSystem;
        this.connector = connector;
    }

    public void sendMenu(Player player){
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        Gui gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # #",
                        "# # a b c d . # #",
                        "# # # # # # # # #")
                .addIngredient('#', border)
                .addIngredient('a', new SendTPPMenuItem(this.tppSystem, this.connector))
                .addIngredient('b', new DeleteTPPMenuItem(player, this.tppSystem))
                .addIngredient('c', new ReceiveTPPMenuItem(this.tppSystem, this.connector))
                .addIngredient('d', new AutoAcceptTPPMenuItem(player, this.connector))
                .build();

        xyz.xenondevs.invui.window.Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("TPP")
                .build();

        window.open();
    }

}