package space.yurisi.universecorev2.subplugins.mywarp.menu.menu_item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp_menu.MywarpInventoryMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class TopItem extends AbstractItem {

    UniverseCoreAPIConnector connector;

    public TopItem(UniverseCoreAPIConnector connector) {
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.GRAY_GLAZED_TERRACOTTA).setDisplayName("トップに戻る");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        MywarpInventoryMenu mywarp_menu = new MywarpInventoryMenu(this.connector);
        mywarp_menu.sendMenu(player);
    }
}