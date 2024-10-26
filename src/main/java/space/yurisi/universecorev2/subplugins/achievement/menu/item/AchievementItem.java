package space.yurisi.universecorev2.subplugins.achievement.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementData;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementDataManager;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class AchievementItem extends AbstractItem {

    private final AchievementData data;

    public AchievementItem(AchievementData data){
        this.data = data;
    }

    @Override
    public ItemProvider getItemProvider() {
        Material material;
        switch (data.getStage()){
            case AchievementDataManager.Achievement_GOLD:
                material = Material.YELLOW_STAINED_GLASS_PANE;
                break;
            case AchievementDataManager.Achievement_SILVER:
                material = Material.WHITE_STAINED_GLASS_PANE;
                break;
            case AchievementDataManager.Achievement_NORMAL:
                material = Material.GLASS_PANE;
                break;
            default:
                return new ItemBuilder(Material.RED_STAINED_GLASS_PANE);
        }
        ItemBuilder builder = new ItemBuilder(material)
                .setDisplayName(data.getItemName());
        for(String lore: data.getItemLore()){
            builder.addLoreLines(lore);
        }
        return builder;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {

    }
}
