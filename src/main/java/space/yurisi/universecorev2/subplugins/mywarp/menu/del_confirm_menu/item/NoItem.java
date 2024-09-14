package space.yurisi.universecorev2.subplugins.mywarp.menu.del_confirm_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.utils.MessageHelper;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class NoItem extends AbstractItem {

    Mywarp mywarp;

    UniverseCoreAPIConnector connector;

    public NoItem(UniverseCoreAPIConnector connector, Mywarp mywarp) {
        this.connector = connector;
        this.mywarp = mywarp;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("いいえ");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        event.getInventory().close();
    }
}