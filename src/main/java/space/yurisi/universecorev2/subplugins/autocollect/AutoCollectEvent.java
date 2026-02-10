package space.yurisi.universecorev2.subplugins.autocollect;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AutoCollectEvent implements Listener {

    private final AutoCollectManager manager;
    private final AutoCollectMessageFormatter messageFormatter;

    public AutoCollectEvent(@NotNull AutoCollectManager manager, @NotNull AutoCollectMessageFormatter messageFormatter) {
        this.manager = manager;
        this.messageFormatter = messageFormatter;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDrop(BlockDropItemEvent event) {
        if (event.isCancelled()) return;
        if (event.getItems().isEmpty()) return;

        Player player = event.getPlayer();

        extractPlayer(player)
                .filter(manager::isAutoCollectEnabled)
                .ifPresent(target -> {
                    collectItems(target, event.getItems());
                    event.getItems().clear();
                });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDrop(@NotNull EntityDropItemEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player player)) return;

        extractPlayer(player)
                .filter(manager::isAutoCollectEnabled)
                .ifPresent(target -> {
                    event.setCancelled(true);
                    collectItems(target, List.of(event.getItemDrop()));
                });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(@NotNull EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        extractPlayer(killer)
                .filter(manager::isAutoCollectEnabled)
                .ifPresent(target -> {
                    collectItemStacks(target, event.getDrops());
                    event.getDrops().clear();
                });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        extractPlayer(event.getPlayer())
                .filter(player -> !manager.isRegistered(player))
                .ifPresent(manager::registerPlayer);
    }

    /**
     * プレイヤーのインベントリにアイテムを収集する
     *
     * @param player 収集対象のプレイヤー
     * @param itemCollection 収集するアイテムのコレクション
     */
    private void collectItems(@NotNull Player player, @NotNull Collection<Item> itemCollection) {
        if (itemCollection.isEmpty()) return;

        ItemStack[] items = itemCollection.stream()
                .map(Item::getItemStack)
                .toArray(ItemStack[]::new);

        PlayerInventory inventory = player.getInventory();
        Map<Integer, ItemStack> failedToAdd = inventory.addItem(items);

        handleFailedItems(player, failedToAdd);
    }

    /**
     * プレイヤーのインベントリにアイテムスタックを収集する
     *
     * @param player 収集対象のプレイヤー
     * @param itemStacks 収集するアイテムスタックのコレクション
     */
    private void collectItemStacks(@NotNull Player player, @NotNull Collection<ItemStack> itemStacks) {
        if (itemStacks.isEmpty()) return;

        ItemStack[] items = itemStacks.toArray(new ItemStack[0]);

        PlayerInventory inventory = player.getInventory();
        Map<Integer, ItemStack> failedToAdd = inventory.addItem(items);

        handleFailedItems(player, failedToAdd);
    }

    /**
     * インベントリに収集できなかったアイテムをプレイヤーの位置にドロップし、通知を送る
     *
     * @param player 対象のプレイヤー
     * @param failedItems 収集に失敗したアイテムのマップ
     */
    private void handleFailedItems(@NotNull Player player, @NotNull Map<Integer, ItemStack> failedItems) {
        if (failedItems.isEmpty()) return;

        failedItems.values().forEach( itemStack ->
                player.getWorld().dropItem(player.getLocation(), itemStack)
        );

        player.sendActionBar(messageFormatter.formatError("インベントリが満杯のため，アイテムをドロップしました"));
    }

    /**
     * Player を Optional でラップして返す
     * Spigot API の Player は null になる可能性があるため Optional で扱うことで null チェックを強制する
     *
     * @param player Spigot API の Player オブジェクト
     * @return Player の Optional ラップ (null の場合は Optional.empty() を返す)
     */
    @NotNull
    private Optional<Player> extractPlayer(Player player) {
        return Optional.ofNullable(player);
    }

}
