package space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.SolarSystemDetailNormalMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class GachaDetailItem extends AbstractItem {

    private ItemStack item;

    public GachaDetailItem(ItemStack item){
        this.item = item;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(this.item);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        SolarSystemDetailNormalMenu menu = new SolarSystemDetailNormalMenu();
        menu.sendMenu(player);
        event.getInventory().close();
    }
}
