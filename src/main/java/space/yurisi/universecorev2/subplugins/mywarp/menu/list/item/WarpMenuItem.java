package space.yurisi.universecorev2.subplugins.mywarp.menu.list.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.Mywarp;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class WarpMenuItem extends AbstractItem {

    Mywarp mywarp;

    public WarpMenuItem(Mywarp mywarp){
        this.mywarp = mywarp;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.MAP).setDisplayName(mywarp.getName());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        player.sendMessage("test");
    }
}
