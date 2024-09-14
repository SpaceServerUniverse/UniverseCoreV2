package space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.tppsystem.TPPSystem;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.receive_request_menu.ReceiveRequestInventoryMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class ReceiveTPPMenuItem extends AbstractItem {

    private final TPPSystem tppSystem;

    private final UniverseCoreAPIConnector connector;

    public ReceiveTPPMenuItem(TPPSystem tppSystem, UniverseCoreAPIConnector connector){
        this.tppSystem = tppSystem;
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.BOOK).setDisplayName("受信したテレポート申請一覧");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        ReceiveRequestInventoryMenu receiveRequestInventoryMenu = new ReceiveRequestInventoryMenu(this.tppSystem, this.connector);
        receiveRequestInventoryMenu.sendMenu(player);
    }
}
