package space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.AutoTppSetting;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class AutoAcceptTPPMenuItem extends AbstractItem {


    private final UniverseCoreAPIConnector connector;

    public AutoAcceptTPPMenuItem(UniverseCoreAPIConnector connector) {
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("自動受信：無効").addLoreLines(
                "§7クリックで自動承認を§2有効§7にします。");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        try {
            if (!connector.isExistsAutoTPPSetting(player)) {
                connector.createAutoTPPSetting(player);
            }
            AutoTppSetting isAutoAcceptTPP = connector.getAutoTPPSettingFromPlayer(player);
            connector.changeAutoTPPSetting(isAutoAcceptTPP, true);
        } catch (UserNotFoundException e) {
            player.sendMessage("ユーザーデータが見つかりませんでした。");
        }
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
        event.getInventory().close();
    }
}
