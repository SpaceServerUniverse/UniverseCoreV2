package space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.send_request_menu.SendRequestInventoryMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class SendTPPMenuItem extends AbstractItem {

    private final RequestManager requestManager;

    private final UniverseCoreAPIConnector connector;
    public SendTPPMenuItem(RequestManager requestManager, UniverseCoreAPIConnector connector){
        this.requestManager = requestManager;
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.ENDER_PEARL).setDisplayName("テレポート申請の送信");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        SendRequestInventoryMenu sendRequestInventoryMenu = new SendRequestInventoryMenu(this.requestManager, this.connector);
        sendRequestInventoryMenu.sendMenu(player);
    }
}
