package space.yurisi.universecorev2.subplugins.mywarp.menu.MywarpMenu.MenuItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.mywarp.menu.AddMywarpMenu.AddMywarpAnvilMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;


public class AddMywarpMenuItem extends AbstractItem {


    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.SPECTRAL_ARROW).setDisplayName("追加");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        AddMywarpAnvilMenu addmenu = new AddMywarpAnvilMenu();
        addmenu.sendMenu(player);

    }

}
