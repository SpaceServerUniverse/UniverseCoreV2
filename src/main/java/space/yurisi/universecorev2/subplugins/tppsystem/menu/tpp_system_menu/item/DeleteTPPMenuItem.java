package space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.tppsystem.TPPSystem;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.UUID;

public class DeleteTPPMenuItem extends AbstractItem {

    private final Player player;
    private final TPPSystem tppSystem;

    private final UUID targetPlayerUUID;

    public DeleteTPPMenuItem(Player player, TPPSystem tppSystem){
        this.player = player;
        this.tppSystem = tppSystem;
        this.targetPlayerUUID = this.tppSystem.getSearchReceiver(player);
    }

    @Override
    public ItemProvider getItemProvider() {
        if (this.targetPlayerUUID == null) {
            return new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName("承認待ちのリクエストはありません。");
        }else {
            String targetPlayerName = this.player.getServer().getOfflinePlayer(this.targetPlayerUUID).getName();
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwningPlayer(this.player);
            item.setItemMeta(meta);
            return new ItemBuilder(item).setDisplayName("承認待ちのリクエスト： " + targetPlayerName).addLoreLines(
                    "§4クリックでリクエストを削除します。"
            );
        }
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        if (this.targetPlayerUUID != null) {
            Player targetPlayer = player.getServer().getPlayer(this.targetPlayerUUID);
            if (targetPlayer == null) {
                player.sendMessage("§4プレイヤーが見つかりませんでした。");
                this.tppSystem.removeAllRequest(this.targetPlayerUUID);
                this.tppSystem.removeSearchReceiver(player);
                return;
            }
            player.sendMessage("§4リクエストを削除しました。");
            this.tppSystem.removeRequest(player, targetPlayer);
            this.tppSystem.removeSearchReceiver(player);
            event.getInventory().close();
        }
    }
}
