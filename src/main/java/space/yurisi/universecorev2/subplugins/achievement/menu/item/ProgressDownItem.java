package space.yurisi.universecorev2.subplugins.achievement.menu.item;

import org.bukkit.Material;
import xyz.xenondevs.invui.gui.ScrollGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.ScrollItem;

public class ProgressDownItem extends ScrollItem {

    public ProgressDownItem() {
        super(1);
    }

    @Override
    public ItemProvider getItemProvider(ScrollGui<?> scrollGui) {
        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§9下にスクロール");
    }
}
