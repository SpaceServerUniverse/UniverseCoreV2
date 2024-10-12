package space.yurisi.universecorev2.menu.menu_item.main_menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.menu.MainMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.List;

public class LaunchNavigationItem extends AbstractItem {

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.MAP)
                .setDisplayName("関連リンク")
                .setLegacyLore(List.of("§6関連リンクを表示します"));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        Bukkit.getServer().dispatchCommand(player, "web");
        Bukkit.getServer().dispatchCommand(player, "wiki");
        Bukkit.getServer().dispatchCommand(player, "discord");
        // メニューを閉じる
        player.closeInventory();
    }
}
