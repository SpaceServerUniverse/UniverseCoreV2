package space.yurisi.universecorev2.subplugins.mywarp.menu.del_confirm_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.utils.MessageHelper;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class YesItem extends AbstractItem {

    Mywarp mywarp;

    UniverseCoreAPIConnector connector;

    public YesItem(UniverseCoreAPIConnector connector, Mywarp mywarp) {
        this.connector = connector;
        this.mywarp = mywarp;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName("はい");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        try {
            connector.deleteMywarp(mywarp);
            player.sendMessage(MessageHelper.getSuccessMessage("ワープポイント" + mywarp.getName() + "を削除しました。"));
        } catch (MywarpNotFoundException e) {
            player.sendMessage(MessageHelper.getErrorMessage("ワープポイントが見つかりませんでした。"));
        }
        event.getInventory().close();
    }
}