package space.yurisi.universecorev2.subplugins.achievement.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementDataManager;
import space.yurisi.universecorev2.subplugins.achievement.menu.item.AchievementItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;

public class AchievementMenu implements BaseMenu {

    @Override
    public void sendMenu(Player player) {
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"));
        Gui gui = PagedGui.items()
                .setStructure(
                        "x x x b b b b b b",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('b', border)
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
            //1st row
            items.add(new AchievementItem(AchievementDataManager.getBreak(player)));
            items.add(new AchievementItem(AchievementDataManager.getPlace(player)));
            items.add(new AchievementItem(AchievementDataManager.getFlower(player)));
            //2nd row
            items.add(new AchievementItem(AchievementDataManager.getOre(player)));
            items.add(new AchievementItem(AchievementDataManager.getCoal(player)));
            items.add(new AchievementItem(AchievementDataManager.getCopper(player)));
            items.add(new AchievementItem(AchievementDataManager.getIron(player)));
            items.add(new AchievementItem(AchievementDataManager.getGold(player)));
            items.add(new AchievementItem(AchievementDataManager.getRedStone(player)));
            items.add(new AchievementItem(AchievementDataManager.getLapis(player)));
            items.add(new AchievementItem(AchievementDataManager.getEmerald(player)));
            items.add(new AchievementItem(AchievementDataManager.getDiamond(player)));
        }
        return items;
    }
}
