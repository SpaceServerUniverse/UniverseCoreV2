package space.yurisi.universecorev2.subplugins.achievement.menu;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementDataManager;
import space.yurisi.universecorev2.subplugins.achievement.menu.item.AchievementItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.window.Window;

import java.util.List;

public class AchievementMenu implements BaseMenu {

    @Override
    public void sendMenu(Player player) {

        Gui gui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(getItems(player))
                .build();

        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("Achievement")
                .build();

        window.open();
    }

    public List<Item> getItems(Player player) {
        List<Item> items = new java.util.ArrayList<>(List.of());
        if(AchievementDataManager.canGetManager()){
            items.add(new AchievementItem(AchievementDataManager.getBreak(player)));
            items.add(new AchievementItem(AchievementDataManager.getPlace(player)));
            items.add(new AchievementItem(AchievementDataManager.getFlower(player)));
        }
        return items;
    }
}
