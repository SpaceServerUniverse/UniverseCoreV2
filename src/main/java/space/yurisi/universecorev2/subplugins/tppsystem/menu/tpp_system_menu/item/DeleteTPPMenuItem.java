package space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.tppsystem.TPPSystem;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.UUID;

public class DeleteTPPMenuItem extends AbstractItem {

    private final Player player;
    private final RequestManager requestManager;

    private final UUID targetPlayerUUID;

    public DeleteTPPMenuItem(Player player, RequestManager requestManager){
        this.player = player;
        this.requestManager = requestManager;
        this.targetPlayerUUID = this.requestManager.getSearchReceiver(player);
    }

    @Override
    public ItemProvider getItemProvider() {
        if (this.targetPlayerUUID == null) {
            return new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName("承認待ちのリクエストはありません。");
        }

        Player targetPlayer = this.player.getServer().getPlayer(this.targetPlayerUUID);
        String targetPlayerName = targetPlayer.getName();
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(targetPlayer.getServer().getOfflinePlayer(targetPlayerName));
        item.setItemMeta(meta);

        return new ItemBuilder(item).setDisplayName("承認待ちのリクエスト： " + targetPlayerName).addLoreLines(
                    "§4クリックでリクエストを削除します。");

    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        if (this.targetPlayerUUID != null) {
            Player targetPlayer = player.getServer().getPlayer(this.targetPlayerUUID);
            if (targetPlayer == null) {
                player.sendMessage("§4プレイヤーが見つかりませんでした。");
                this.requestManager.removeAllRequest(this.targetPlayerUUID);
                this.requestManager.removeSearchReceiver(player);
                return;
            }
            player.sendMessage("§4リクエストを削除しました。");
            this.requestManager.removeRequest(player, targetPlayer);
            this.requestManager.removeSearchReceiver(player);
            event.getInventory().close();
        }
    }
}
