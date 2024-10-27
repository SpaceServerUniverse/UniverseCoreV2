package space.yurisi.universecorev2.subplugins.achievement.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementDataManager;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.Objects;

public class ProgressBarItem extends AbstractItem {

    private final boolean isAchieved;
    private final String stage;
    private final String name;

    public ProgressBarItem(boolean isAchieved, String stage, String name) {
        this.isAchieved = isAchieved;
        this.stage = stage;
        this.name = name;
    }

    @Override
    public ItemProvider getItemProvider() {
        Material material;
        if(Objects.equals(stage, AchievementDataManager.Achievement_NORMAL)){
            material = isAchieved ? Material.LIGHT_BLUE_STAINED_GLASS_PANE:Material.LIGHT_GRAY_STAINED_GLASS_PANE;
        }else{
            material = isAchieved ? Material.BLUE_STAINED_GLASS_PANE:Material.LIGHT_BLUE_STAINED_GLASS_PANE;
        }
        return new ItemBuilder(material).setDisplayName(name);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {

    }
}
