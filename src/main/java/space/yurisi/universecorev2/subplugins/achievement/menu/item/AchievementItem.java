package space.yurisi.universecorev2.subplugins.achievement.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.achievement.data.AchievementData;
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
        if(data == null) {
            return new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        }
        ItemBuilder builder = new ItemBuilder(data.getMaterial())
                .setDisplayName(data.getItemName());
        for(String lore: data.getItemLore()){
            builder.addLoreLines(lore);
        }
        return builder;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        //クリックで実績解除報酬をプレゼント！とかあったらいいなと思った午前2時
    }
}
