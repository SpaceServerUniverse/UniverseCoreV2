package space.yurisi.universecorev2.subplugins.fishingsystem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishFeed;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishingRarity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Fish {
    private String name;
    private double min = 0;
    private double max = 0;
    private double size = 0;
    private FishingRarity rarity;
    private FishFeed feed;
    private ItemStack baseItem;

    public Fish(String name, FishingRarity rarity, FishFeed feed, ItemStack baseItem) {
        this.name = name;
        this.rarity = rarity;
        this.feed = feed;
        this.baseItem = baseItem;
    }

    // コンストラクタ
    public Fish(String name, double min, double max, FishingRarity rarity, FishFeed feed, ItemStack baseItem) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.rarity = rarity;
        this.feed = feed;
        this.baseItem = baseItem;
    }

    // Getter メソッド
    public String getName() {
        return name;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public FishingRarity getRarity() {
        return rarity;
    }

    public FishFeed getFeed() {
        return feed;
    }

    public double getSize(){
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public ItemStack getBaseItem(String world_name) {
        ItemStack item = this.baseItem;
        ItemMeta meta = baseItem.getItemMeta();
        double randomDouble = min + (Math.random() * (max - min));

        BigDecimal bd = new BigDecimal(randomDouble);
        BigDecimal size = bd.setScale(1, RoundingMode.HALF_DOWN);
        setSize(size.doubleValue());

        if (meta == null) {
            return item;
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.FISH), PersistentDataType.STRING, name);

        String displayName = getTextColorFromReality() + name;
        meta.displayName(Component.text(displayName));
        List<String> lore = new ArrayList<>();
        lore.add("レア度: " + FishingRarity.getFishFeedJapanese(rarity));
        lore.add("釣れた場所: " + world_name);
        if (this.rarity == FishingRarity.UltraRare) {
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.FISH_SIZE), PersistentDataType.DOUBLE, size.doubleValue());
            lore.add("サイズ: " + size.doubleValue() + "cm");
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public String getTextColorFromReality() {
        switch (this.rarity) {
            case Rare -> {
                return "§b";
            }
            case SuperRare -> {
                return "§c";
            }
            case UltraRare -> {
                return "§d";
            }
            default -> {
                return "§a";
            }
        }
    }
}
