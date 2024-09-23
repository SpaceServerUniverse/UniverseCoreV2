package space.yurisi.universecorev2.menu.menu_item;

import org.bukkit.Material;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class ForwardItem extends PageItem {

    public ForwardItem() {
        super(true);
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