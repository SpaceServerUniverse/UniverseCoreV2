package space.yurisi.universecorev2.subplugins.mywarp.menu.del_confirm_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.del_confirm_menu.DelConfirmMywarpInventoryMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class MywarpItem extends AbstractItem {

    Mywarp mywarp;

    public MywarpItem(Mywarp mywarp){
        this.mywarp = mywarp;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.MAP).setDisplayName(mywarp.getName()).addLoreLines(
                "x:"+mywarp.getX(),
                "y:"+mywarp.getY(),
                "z:"+mywarp.getZ(),
                "world:"+mywarp.getWorld_name(),
                "公開:"+ (mywarp.getIs_private() ? "公開" : "非公開"));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
    }
}
