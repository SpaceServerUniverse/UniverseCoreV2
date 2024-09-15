package space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class AutoAcceptTPPMenuItem extends AbstractItem {

    private final Player player;

    private final UniverseCoreAPIConnector connector;

    public AutoAcceptTPPMenuItem(Player player, UniverseCoreAPIConnector connector){
        this.player = player;
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        Boolean isAutoAcceptTPP = false;

        if (!connector.isExistsAutoTPPSetting(player)) {
            player.sendMessage("§2設定を作成します。");
            try {
                connector.createDefaultAutoTPPSetting(player, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                isAutoAcceptTPP = connector.getAutoTPPSetting(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (isAutoAcceptTPP) {
            return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("自動受信：有効").addLoreLines(
                    "§7クリックで自動承認を§4無効§7にします。"
            );
        }

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("自動受信：無効").addLoreLines(
                    "§7クリックで自動承認を§2有効§7にします。");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        try {
            Boolean isAutoAcceptTPP = connector.getAutoTPPSetting(player);
            connector.createDefaultAutoTPPSetting(player, !isAutoAcceptTPP);
        } catch (Exception e) {
            connector.createAutoTPPSetting(player);
        }
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
        event.getInventory().close();
    }
}
