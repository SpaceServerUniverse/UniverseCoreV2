package space.yurisi.universecorev2.subplugins.tppsystem.menu.send_request_menu.item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.tppsystem.TPPSystem;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class SendRequestMenuItem extends AbstractItem {

    private final Player player;
    private final RequestManager requestManager;
    private String playerName;

    private final UniverseCoreAPIConnector connector;

    public SendRequestMenuItem(@NotNull Player player, RequestManager requestManager, String playerName, UniverseCoreAPIConnector connector){
        this.player = player;
        this.requestManager = requestManager;
        this.playerName = playerName;
        this.connector = connector;
    }

    @Override
    public ItemProvider getItemProvider() {
        //該当プレイヤーの頭を表示
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player.getServer().getOfflinePlayer(playerName));
        item.setItemMeta(meta);
        return new ItemBuilder(item).setDisplayName(playerName);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {

        Player targetPlayer = player.getServer().getPlayer(playerName);

        if(targetPlayer == null){
            player.sendMessage("§6" + playerName + " §3はオフラインです。");
            return;
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        player.sendMessage("§6" + playerName + " §2にテレポート申請を送信しました。");

        if (!connector.isExistsAutoTPPSetting(targetPlayer)) {
            this.sendRequest(player, targetPlayer);
            return;
        }

        try {
            if (connector.isAutoAccept(targetPlayer)) {
                player.teleport(targetPlayer);
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                targetPlayer.sendMessage("§6" + player.getName() + " §2からのテレポート申請を自動承認しました。");
                player.sendMessage("§6" + targetPlayer.getName() + " §2にテレポートしました。");
            } else {
                this.sendRequest(player, targetPlayer);
            }
        } catch (UserNotFoundException e) {
            player.sendMessage("相手のユーザーデータが見つかりませんでした。");
        }
    }

    private void sendRequest(Player player, Player targetPlayer) {
        targetPlayer.sendMessage("§6" + player.getName() + " §2からテレポート申請が届きました。");
        targetPlayer.sendMessage("§6/tppメニューから承認可否を選択できます。");
        this.requestManager.setRequest(player, targetPlayer);
        this.requestManager.setSearchReceiver(player, targetPlayer);
    }
}
