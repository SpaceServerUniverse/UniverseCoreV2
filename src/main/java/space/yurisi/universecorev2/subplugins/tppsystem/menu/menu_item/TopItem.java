package space.yurisi.universecorev2.subplugins.tppsystem.menu.menu_item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.TPPSystemInventoryMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class TopItem extends AbstractItem {

    private final RequestManager requestManager;

    private final UniverseCoreAPIConnector connector;

    public TopItem(RequestManager requestManager, UniverseCoreAPIConnector connector){
        this.requestManager = requestManager;
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.GRAY_GLAZED_TERRACOTTA).setDisplayName("トップに戻る");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        TPPSystemInventoryMenu tppSystemInventoryMenu = new TPPSystemInventoryMenu(this.requestManager, this.connector);
        tppSystemInventoryMenu.sendMenu(player);
    }
}
