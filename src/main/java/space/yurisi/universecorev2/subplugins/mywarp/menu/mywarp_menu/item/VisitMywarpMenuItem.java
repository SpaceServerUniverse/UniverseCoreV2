package space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp_menu.item;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.mywarp.menu.visit_menu.VisitMywarpAnvilMenu;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class VisitMywarpMenuItem extends AbstractItem {

    private UniverseCoreAPIConnector connector;

    public VisitMywarpMenuItem(UniverseCoreAPIConnector connector){
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.FEATHER).setDisplayName("訪問");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        VisitMywarpAnvilMenu visitMenu = new VisitMywarpAnvilMenu(this.connector);
        visitMenu.sendMenu(player);
    }
}
