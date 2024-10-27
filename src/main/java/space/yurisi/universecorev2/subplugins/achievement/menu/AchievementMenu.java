package space.yurisi.universecorev2.subplugins.achievement.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementDataManager;
import space.yurisi.universecorev2.subplugins.achievement.menu.item.AchievementItem;
import space.yurisi.universecorev2.subplugins.achievement.menu.item.ProgressMenuItem;
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
        Item border = new SimpleItem(new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
        Gui gui = PagedGui.items()
                .setStructure(
                        "1 x x x x x x b b",
                        "2 x x x x x x x x",
                        "3 x x x b b b b b"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('1', new ProgressMenuItem(1))
                .addIngredient('2', new ProgressMenuItem(2))
                .addIngredient('3', new ProgressMenuItem(3))
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

        //1st row
        items.add(new AchievementItem(AchievementDataManager.getLogin(player)));
        items.add(new AchievementItem(AchievementDataManager.getConsecutiveLogin(player)));
        items.add(new AchievementItem(AchievementDataManager.getBreak(player)));
        items.add(new AchievementItem(AchievementDataManager.getPlace(player)));
        items.add(new AchievementItem(AchievementDataManager.getFlower(player)));
        items.add(new AchievementItem(AchievementDataManager.getOre(player)));
        //2nd row
        items.add(new AchievementItem(AchievementDataManager.getCoal(player)));
        items.add(new AchievementItem(AchievementDataManager.getCopper(player)));
        items.add(new AchievementItem(AchievementDataManager.getIron(player)));
        items.add(new AchievementItem(AchievementDataManager.getGold(player)));
        items.add(new AchievementItem(AchievementDataManager.getRedStone(player)));
        items.add(new AchievementItem(AchievementDataManager.getLapis(player)));
        items.add(new AchievementItem(AchievementDataManager.getEmerald(player)));
        items.add(new AchievementItem(AchievementDataManager.getDiamond(player)));
        //3rd row
        items.add(new AchievementItem(AchievementDataManager.getGacha(player)));
        items.add(new AchievementItem(AchievementDataManager.getKill(player)));
        items.add(new AchievementItem(AchievementDataManager.getFishing(player)));

        return items;
    }
}
