package space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.SolarSystemDetailNormalMenu;
import space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.SolarSystemDetailRareMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class NormalItemMenuItem extends AbstractItem {

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.WHITE_WOOL).setDisplayName("§lノーマル");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        new SolarSystemDetailNormalMenu().sendMenu(player);
        event.getInventory().close();
    }
}