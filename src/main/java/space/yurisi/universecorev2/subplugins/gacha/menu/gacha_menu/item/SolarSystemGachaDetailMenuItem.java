package space.yurisi.universecorev2.subplugins.gacha.menu.gacha_menu.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.gacha.menu.solar_system_detail_menu.SolarSystemDetailNormalMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class SolarSystemGachaDetailMenuItem extends AbstractItem {

    public SolarSystemGachaDetailMenuItem() {
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.BLACK_WOOL).setDisplayName("§e§lSolarSystemガチャ！").addLoreLines(
                "このガチャからは",
                "レベルピッケルやレベル剣、装備など",
                "有用なアイテムが手に入ります！");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        new SolarSystemDetailNormalMenu().sendMenu(player);
        event.getInventory().close();
    }
}