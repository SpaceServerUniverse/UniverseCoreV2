package space.yurisi.universecorev2.subplugins.mywarp.menu.list.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class ForwardItem extends ControlItem<PagedGui<?>> {

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        getGui().goForward();
    }


    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {
        ItemBuilder builder = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE);
        builder.setDisplayName("次のページ")
                .addLoreLines(gui.hasNextPage()
                        ? (gui.getCurrentPage() + 2) + "/" + gui.getPageAmount()
                        : "最後のページです");

        return builder;
    }

}