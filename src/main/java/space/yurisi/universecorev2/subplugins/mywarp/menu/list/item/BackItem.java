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

public class BackItem extends ControlItem<PagedGui<?>> {

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        getGui().goBack();
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {
        ItemBuilder builder = new ItemBuilder(Material.RED_STAINED_GLASS_PANE);
        builder.setDisplayName("前のページ")
                .addLoreLines(gui.hasPreviousPage()
                        ? gui.getCurrentPage() + "/" + gui.getPageAmount()
                        : "最初のページ");

        return builder;
    }

}