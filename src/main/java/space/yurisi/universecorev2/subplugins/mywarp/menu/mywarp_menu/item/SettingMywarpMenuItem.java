package space.yurisi.universecorev2.subplugins.mywarp.menu.mywarp_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.setting_menu.SettingMywarpInventoryMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class SettingMywarpMenuItem extends AbstractItem {

    private UniverseCoreAPIConnector connector;

    public SettingMywarpMenuItem(UniverseCoreAPIConnector connector){
        this.connector = connector;
    }


    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.BOOK).setDisplayName("公開設定");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        SettingMywarpInventoryMenu setting_menu = new SettingMywarpInventoryMenu(this.connector);
        setting_menu.sendMenu(player);

    }

}
