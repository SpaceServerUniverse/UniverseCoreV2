package space.yurisi.universecorev2.subplugins.achievement.menu;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.window.Window;

public class AchievementMenu implements BaseMenu {

    @Override
    public void sendMenu(Player player) {
        Gui gui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .build();

        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("Achievement")
                .build();

        window.open();
    }
}
