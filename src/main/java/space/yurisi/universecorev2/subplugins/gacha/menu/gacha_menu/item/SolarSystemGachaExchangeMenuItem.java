package space.yurisi.universecorev2.subplugins.gacha.menu.gacha_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.gacha.constrants.GachaType;
import space.yurisi.universecorev2.subplugins.gacha.menu.exchange_menu.GachaExchangeMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class SolarSystemGachaExchangeMenuItem extends AbstractItem {

    public SolarSystemGachaExchangeMenuItem() {}

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.PAPER).setDisplayName("§e§lハズレ券交換").addLoreLines(
                "SolarSystemイベントガチャの",
                "ハズレ券を100枚使って",
                "好きなURと交換できます！");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        GachaExchangeMenu menu = new GachaExchangeMenu();
        menu.sendMenu(player, GachaType.SOLAR_SYSTEM);
    }
}
