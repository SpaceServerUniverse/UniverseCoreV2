package space.yurisi.universecorev2.subplugins.fishingsystem.fishlist;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.fishingrod.FishingRod;
import space.yurisi.universecorev2.item.pickaxe.FishingPickaxe;
import space.yurisi.universecorev2.item.repair_cream.RepairCream;
import space.yurisi.universecorev2.subplugins.fishingsystem.Fish;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishFeed;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishingRarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public abstract class BaseFishList {

    protected String worldName;

    protected List<Fish> fishes = new ArrayList<>();

    public BaseFishList() {
        initFishes();
        setWorldName("謎の場所");
    }

    protected void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    protected abstract void initFishes();

    public Fish getFishByRarityAndFeed(FishingRarity rarity, FishFeed feed) {
        List<Fish> collection = fishes.stream()
                .filter(fish -> fish.getRarity() == rarity)
                .filter(fish -> fish.getFeed() == feed || fish.getFeed() == FishFeed.NOTHING)
                .toList();

        if (collection.isEmpty()) {
            throw new IllegalArgumentException("そのレアリティの魚がいませんでした。");
        }

        Random random = new Random();
        return collection.get(random.nextInt(collection.size()));
    }

    public ItemStack catchFish(Player player, int level, FishFeed feed) {
        Random rand = new Random();
        int num = rand.nextInt(1000);
        HashMap<FishingRarity, Integer> percentages = FishingRod.getRarityPercentages(level);

        int pickaxe = percentages.get(FishingRarity.PickaxeRare);
        int cream = pickaxe + percentages.get(FishingRarity.CreamRare);
        int ur = cream + percentages.get(FishingRarity.UltraRare);
        int sr = ur + percentages.get(FishingRarity.SuperRare);
        int r = sr + percentages.get(FishingRarity.Rare);

        ItemStack item;
        if (num < pickaxe) {
            item = UniverseItem.getItem(FishingPickaxe.id).getItem();
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, 1, 1);
            Bukkit.broadcast(Component.text("§a" + player.getName() + "§eが謎のピッケルを釣り上げた！"));
            player.sendActionBar(Component.text("[釣りAI]§a輝いてる§c§l謎のピッケル§r§aが釣れた！"));
        } else if (num < cream) {
            item = UniverseItem.getItem(RepairCream.id).getItem();
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, 1, 1);
            Bukkit.broadcast(Component.text("§a" + player.getName() + "§eが修復クリームを釣り上げた！"));
            player.sendActionBar(Component.text("[釣りAI]§aねばねばの§c§l修復クリーム§r§aが釣れた！"));
        } else if (num < ur) {
            Fish fish = getFishByRarityAndFeed(FishingRarity.UltraRare, feed);
            item = fish.getBaseItem(worldName);
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, 1, 1);
            Bukkit.broadcast(Component.text("§a" + player.getName() + "§eが" + worldName + "で激レアの§l§c" + fish.getName() + "(" + fish.getSize() + "cm)§r§eを釣りあげた！！！！！"));
            player.sendActionBar(Component.text("[釣りAI]§eこれは！！とんでもない！！§5§l" + fish.getName() + "(" + fish.getSize() + "cm)§r§eが釣れた！"));
        } else if (num < sr) {
            Fish fish = getFishByRarityAndFeed(FishingRarity.SuperRare, feed);
            item = fish.getBaseItem(worldName);
            player.sendActionBar(Component.text("[釣りAI]§eわお！！" + worldName + "の§d§l" + fish.getName() + "§r§eが釣れた！"));
        } else if (num < r) {
            Fish fish = getFishByRarityAndFeed(FishingRarity.Rare, feed);
            item = fish.getBaseItem(worldName);
            player.sendActionBar(Component.text("[釣りAI]§eおっ！§c§l" + fish.getName() + "§r§eが釣れた！"));
        } else {
            Fish fish = getFishByRarityAndFeed(FishingRarity.Normal, feed);
            item = fish.getBaseItem(worldName);
            player.sendActionBar(Component.text("[釣りAI]§a§b§l" + fish.getName() + "§r§aが釣れた！"));
        }
        return item;
    }
}
