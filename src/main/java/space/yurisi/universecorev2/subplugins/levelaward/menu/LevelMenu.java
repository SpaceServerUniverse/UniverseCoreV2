package space.yurisi.universecorev2.subplugins.levelaward.menu;

import space.yurisi.universecorev2.subplugins.levelaward.LevelAward;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.levelaward.menu.item.TicketItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;


public class LevelMenu implements BaseMenu {
    @Override
    public void sendMenu(Player player) {
        List<Item> items = new java.util.ArrayList<>(List.of());
        for (int i = 0; i < (LevelAward.getInstance().getConfig().getTicket(player.getUniqueId().toString())/64); i++) {
            items.add(new TicketItem(player,64));
        }

        items.add(new TicketItem(player,LevelAward.getInstance().getConfig().getTicket(player.getUniqueId().toString())%64));
        Gui gui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(items)
                .build();

        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("LevelAward")
                .build();

        window.open();
    }
}
