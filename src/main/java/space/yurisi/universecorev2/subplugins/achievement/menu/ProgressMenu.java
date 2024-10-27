package space.yurisi.universecorev2.subplugins.achievement.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementData;
import space.yurisi.universecorev2.subplugins.achievement.menu.item.*;
import xyz.xenondevs.invui.gui.ScrollGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;

public class ProgressMenu implements BaseMenu{

    private final List<AchievementData> dataList;

    public ProgressMenu(List<AchievementData> dataList){
        this.dataList = dataList;
    }

    @Override
    public void sendMenu(Player player) {
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"));
        ScrollGui.Builder<@NotNull Item> builder;
        builder = ScrollGui.items()
                .setStructure(
                        "x x x x x x x x u",
                        "x x x x x x x x b",
                        "x x x x x x x x h",
                        "x x x x x x x x b",
                        "x x x x x x x x d"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('b', border)
                .addIngredient('u', new ProgressUpItem())
                .addIngredient('d', new ProgressDownItem())
                .addIngredient('h', new AchievementHomeItem());

        for(int i = 0; i < (this.dataList.size() * 8); i++){
            if(i % 8 == 0){
                builder = builder.addContent(new AchievementItem(this.dataList.get(i / 8)));
                continue;
            }
            int j = i / 8;
            AchievementData data = this.dataList.get(j);
            Long currentCount = data.getCurrentCount();
            Long currentGoal = data.getCurrentGoal();
            int cell = (int) (currentGoal / 8);
            int achievedCells = (int) (currentCount / cell);
            builder = builder.addContent(new ProgressBarItem((i % 8) <= achievedCells, data.getStage(), data.getItemName()));
        }

        Window window = Window.single()
                .setViewer(player)
                .setGui(builder.build())
                .setTitle("Achievement_Progress")
                .build();

        window.open();
    }
}
