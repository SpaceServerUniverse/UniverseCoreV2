package space.yurisi.universecorev2.subplugins.gacha.menu.gacha_menu.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.gacha.core.event.SolarSystemEventGacha;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class SolarSystemGachaMenuItem extends AbstractItem {

    public SolarSystemGachaMenuItem() {
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.DIAMOND).setDisplayName("§e§lSolarSystemガチャ！").addLoreLines(
                "このガチャからは",
                "レベルピッケルやレベル剣、装備など",
                "有用なアイテムが手に入ります！");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        new SolarSystemEventGacha(player).turn(1);
        event.getInventory().close();
    }
}
