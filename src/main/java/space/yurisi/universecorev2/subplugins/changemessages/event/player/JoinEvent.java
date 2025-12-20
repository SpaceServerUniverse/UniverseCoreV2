package space.yurisi.universecorev2.subplugins.changemessages.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.book.MainMenuBook;
import space.yurisi.universecorev2.item.book.TutorialBook;
import space.yurisi.universecorev2.subplugins.changemessages.data.SuicidePlayerData;
import space.yurisi.universecorev2.subplugins.changemessages.file.Config;

public final class JoinEvent implements Listener {

    private final Config config;

    private final Plugin plugin;

    public JoinEvent(Config config, Plugin plugin) {
        this.config = config;
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SuicidePlayerData.getInstance().register(player);
        player.sendTitle("§eWelcome to SpaceServer", "- Universe -");
        if (!player.hasPlayedBefore()) {
            event.joinMessage(getFirstJoinMessage(player));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 10.0f, 1.0f);
            giveNewUserItems(player);
            return;
        }
        event.joinMessage(getUserCustomJoinMessage(player));


        if(player.getWorld().getName().equals("lobby")){
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setAllowFlight(true);
                }
            }.runTaskLater(plugin, 10);
        }
    }

    private Component getFirstJoinMessage(Player player) {
        return Component.text("§a[入室] §a§l 初in §r§bの§c" + player.getName() + "§b様が§a§lオンライン§r§bになりました");
    }

    private void giveNewUserItems(Player player) {
        PlayerInventory inventory = player.getInventory();

        inventory.addItem(ItemStack.of(Material.LEATHER_HELMET));
        inventory.addItem(ItemStack.of(Material.LEATHER_CHESTPLATE));
        inventory.addItem(ItemStack.of(Material.LEATHER_LEGGINGS));
        inventory.addItem(ItemStack.of(Material.LEATHER_BOOTS));

        inventory.addItem(ItemStack.of(Material.STONE_SWORD));
        inventory.addItem(ItemStack.of(Material.STONE_PICKAXE));
        inventory.addItem(ItemStack.of(Material.STONE_AXE));
        inventory.addItem(ItemStack.of(Material.STONE_SHOVEL));
        inventory.addItem(ItemStack.of(Material.STONE_HOE));

        inventory.addItem(ItemStack.of(Material.BREAD, 32));

        CustomItem item = UniverseItem.getItem(MainMenuBook.id);
        if(item != null) {
            inventory.addItem(item.getItem());
        }

        CustomItem tutorialBook = UniverseItem.getItem(TutorialBook.id);
        if(tutorialBook != null) {
            inventory.addItem(tutorialBook.getItem());
        }

    }

    private Component getUserCustomJoinMessage(Player player) {
        if (!config.existsCustomJoinMessage(player)) {
            return Component.text(
                    "§a[入室] §c" + player.getName() + "§e様が§a§lオンライン§r§eになりました"
            );
        }
        return Component.text(
                "§a[入室] §c" + config.getCustomJoinMessage(player)
        );
    }
}
