package space.yurisi.universecorev2.subplugins.fishingsystem.event;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.LevellingCustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.fishingrod.FishingRod;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishFeed;
import space.yurisi.universecorev2.subplugins.fishingsystem.fishlist.EarthFishList;
import space.yurisi.universecorev2.subplugins.fishingsystem.fishlist.LobbyFishList;
import space.yurisi.universecorev2.utils.StringHelper;

import java.util.List;

public class FishEvent implements Listener {

    @EventHandler
    public void FishEvent(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();
        ItemStack offhand = player.getInventory().getItemInOffHand();

        if (event.getCaught() == null) {
            return;
        }

        Item caught = (Item) event.getCaught();
        ItemStack item = caught.getItemStack();
        ItemMeta meta = item.getItemMeta();

        if (!UniverseItem.isUniverseItemFromId(hand, FishingRod.id) || !UniverseItem.isLevelingItem(hand)) {
            return;
        }

        int level = LevellingCustomItem.getLevel(hand);

        FishFeed feed = FishFeed.getPlayerFishFeed(offhand.getType());

        if(!canUseFishFeed(player)){
           return;
        }

        ItemStack fish;
        switch (player.getLocation().getWorld().getName()) {
            case "earth":
                fish = new EarthFishList().catchFish(player, level, feed);
                break;
            case "lobby":
                fish = new LobbyFishList().catchFish(player, level, feed);
                break;
            default:
                return;
        }

        item.setType(fish.getType());
        Component name = fish.displayName();
        String displayName = StringHelper.getComponent2String(name);
        List<Component> lore = fish.lore();
        meta.displayName(Component.text(displayName));
        meta.lore(lore);
        item.setItemMeta(meta);
    }

    private static boolean canUseFishFeed(Player player) {
        ItemStack item = player.getInventory().getItemInOffHand();
        FishFeed feed = FishFeed.getPlayerFishFeed(item.getType());

        if (feed == FishFeed.NOTHING) return true;

        if (item.getAmount() <= 0) {
            Bukkit.broadcastMessage("§a餌(" + FishFeed.getFishFeedJapanese(item.getType()) + ")が切れています。");
            return false;
        }

        item.setAmount(item.getAmount() - 1);
        return true;
    }
}

