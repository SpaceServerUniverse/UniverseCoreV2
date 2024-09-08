package space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.list_menu.ListMywarpInventoryMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class ListMywarpMenuItem extends AbstractItem {

    private UniverseCoreAPIConnector connector;

    public ListMywarpMenuItem(UniverseCoreAPIConnector connector){
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.MAP).setDisplayName("ワープ");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        ListMywarpInventoryMenu listMywarpInventoryMenu = new ListMywarpInventoryMenu(this.connector);
        listMywarpInventoryMenu.sendMenu(player);

    }

}
