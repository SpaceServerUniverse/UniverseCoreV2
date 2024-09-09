package space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.add_menu.AddMywarpAnvilMenu;
import space.yurisi.universecorev2.subplugins.mywarp.menu.del_menu.DelMywarpInventoryMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class DelMywarpMenuItem extends AbstractItem {

    UniverseCoreAPIConnector connector;

    public DelMywarpMenuItem(UniverseCoreAPIConnector connector){
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName("削除");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        DelMywarpInventoryMenu del = new DelMywarpInventoryMenu(this.connector);
        del.sendMenu(player);

    }

}