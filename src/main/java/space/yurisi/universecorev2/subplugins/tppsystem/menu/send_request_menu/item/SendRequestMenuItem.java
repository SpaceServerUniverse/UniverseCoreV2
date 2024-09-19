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
import space.yurisi.universecorev2.utils.Message;
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
            Message.sendErrorMessage(player, "[テレポートAI]", "プレイヤーが見つかりませんでした．");
            return;
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        Message.sendNormalMessage(player, "[テレポートAI]", "§6" + playerName + " §fにテレポート申請を送信しました．");

        if (!connector.isExistsAutoTPPSetting(targetPlayer)) {
            this.sendRequest(player, targetPlayer);
            event.getInventory().close();
            return;
        }

        try {
            if (connector.isAutoAccept(targetPlayer)) {
                player.teleport(targetPlayer);
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                Message.sendSuccessMessage(targetPlayer, "[テレポートAI]", "§6" + player.getName() + " §aからのテレポート申請を自動承認しました。");
                Message.sendSuccessMessage(player, "[テレポートAI]", "§6" + targetPlayer.getName() + " §aにテレポートしました。");
            } else {
                this.sendRequest(player, targetPlayer);
            }
            event.getInventory().close();
        } catch (UserNotFoundException e) {
            Message.sendWarningMessage(player, "[テレポートAI]", "相手のユーザーデータが見つかりませんでした．");
        }
        event.getInventory().close();
    }

    private void sendRequest(Player player, Player targetPlayer) {
        targetPlayer.playSound(targetPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2, 1);
        Message.sendNormalMessage(targetPlayer, "[テレポートAI]", "§6" + player.getName() + " §fからテレポート申請が届きました．");
        Message.sendNormalMessage(targetPlayer, "[テレポートAI]", "§6/tpp§fメニューから承認可否を選択できます．");
        Message.sendNormalMessage(targetPlayer, "[テレポートAI]", "コマンドからも承認可能です．");
        Message.sendNormalMessage(player, "[テレポートAI]", "§6/tpp accept " + targetPlayer.getName() + " §fで承認");
        Message.sendNormalMessage(player, "[テレポートAI]", "§6/tpp deny " + targetPlayer.getName() + " §fで拒否");
        this.requestManager.setRequest(player, targetPlayer);
        this.requestManager.setSearchReceiver(player, targetPlayer);
    }
}
