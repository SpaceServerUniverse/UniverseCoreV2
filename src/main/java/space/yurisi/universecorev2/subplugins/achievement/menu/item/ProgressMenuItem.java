package space.yurisi.universecorev2.subplugins.achievement.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementDataManager;
import space.yurisi.universecorev2.subplugins.achievement.menu.ProgressMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.List;

public class ProgressMenuItem extends AbstractItem {

    private final Integer row;

    public ProgressMenuItem(Integer row) {
        this.row = row;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§a"+row+"列目: 進行状況を表示");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if(!AchievementDataManager.canGetManager()) return;
        ProgressMenu menu;
        switch (row){
            case 1:
                menu = new ProgressMenu(List.of(
                        AchievementDataManager.getLogin(player),
                        AchievementDataManager.getConsecutiveLogin(player),
                        AchievementDataManager.getBreak(player),
                        AchievementDataManager.getPlace(player),
                        AchievementDataManager.getFlower(player),
                        AchievementDataManager.getOre(player)
                ));
                menu.sendMenu(player);
                break;
            case 2:
                menu = new ProgressMenu(List.of(
                        AchievementDataManager.getCoal(player),
                        AchievementDataManager.getCopper(player),
                        AchievementDataManager.getIron(player),
                        AchievementDataManager.getGold(player),
                        AchievementDataManager.getRedStone(player),
                        AchievementDataManager.getLapis(player),
                        AchievementDataManager.getEmerald(player),
                        AchievementDataManager.getDiamond(player)
                        ));
                menu.sendMenu(player);
                break;
            case 3:
                menu = new ProgressMenu(List.of(
                        AchievementDataManager.getGacha(player),
                        AchievementDataManager.getKill(player),
                        AchievementDataManager.getFishing(player)
                ));
                menu.sendMenu(player);
                break;
        }
    }
}
