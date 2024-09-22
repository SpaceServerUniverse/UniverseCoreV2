package space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.SolarSystemDetailRareMenu;
import space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.SolarSystemDetailUltraRareMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class UltraRareItemMenuItem extends AbstractItem {

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.RED_WOOL).setDisplayName("§c§lウルトラレア");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        new SolarSystemDetailUltraRareMenu().sendMenu(player);
        event.getInventory().close();
    }
}