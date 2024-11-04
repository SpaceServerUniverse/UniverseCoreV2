package space.yurisi.universecorev2.subplugins.achievement.menu.item;

import org.bukkit.Material;
import xyz.xenondevs.invui.gui.ScrollGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.ScrollItem;

public class ProgressUpItem extends ScrollItem {

    public ProgressUpItem(){
        super(-1);
    }

    @Override
    public ItemProvider getItemProvider(ScrollGui<?> scrollGui) {
        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§9上にスクロール");
    }
}
