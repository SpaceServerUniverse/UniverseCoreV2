package space.yurisi.universecorev2.subplugins.tppsystem.menu.receive_request_menu.item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import space.yurisi.universecorev2.subplugins.tppsystem.TPPSystem;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.UUID;

public class ReceiveRequestMenuItem extends AbstractItem {

    private final Player player;
    private final TPPSystem tppSystem;
    private final Player targetPlayer;

    public ReceiveRequestMenuItem(@NotNull Player player, TPPSystem tppSystem, UUID targetUUID){
        this.player = player;
        this.tppSystem = tppSystem;
        this.targetPlayer = player.getServer().getPlayer(targetUUID);
    }

    @Override
    public ItemProvider getItemProvider() {
        //該当プレイヤーの頭を表示
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(this.targetPlayer);
        item.setItemMeta(meta);
        return new ItemBuilder(item).setDisplayName(this.targetPlayer.getName()).addLoreLines(
                "§2左クリックでテレポートを許可",
                "§4右クリックでテレポートを拒否"
        );
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {


            String playerName = this.targetPlayer.getName();
//            if (targetPlayer == null) {
//                throw new UserNotFoundException("申請中のプレイヤーはいません.");
//            }
            if (clickType == ClickType.LEFT) {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.sendMessage("§6" + playerName + " §2のテレポート申請を許可しました.");

                targetPlayer.sendMessage("§6" + player.getName() + " §2にテレポートします.");
                targetPlayer.teleport(player);
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            }else if (clickType == ClickType.RIGHT) {
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                player.sendMessage("§6" + playerName + " §2のテレポート申請を拒否しました.");
                targetPlayer.sendMessage("§6" + player.getName() + " §2にテレポートを拒否されました.");
            }
            this.tppSystem.removeSearchReceiver(targetPlayer);
            this.tppSystem.removeRequest(targetPlayer, player);

            // メニューを更新
            this.tppSystem.updateRequest(player);
            event.getInventory().close();
    }
}
