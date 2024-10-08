package space.yurisi.universecorev2.item.fishingrod;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.LevellingCustomItem;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishingRarity;

import java.util.HashMap;

public class FishingRod extends LevellingCustomItem {

    public static final String id = "fishing_rod";

    public FishingRod(){
        super(
                id,
                "§a§l特殊釣り竿",
                1,
                4,
                ItemStack.of(Material.FISHING_ROD)
        );
        registerLevelingItemFunctions();
    }

    @Override
    protected void registerLevelingItemFunctions() {
        leveling_settings.put(1, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.SHARPNESS, 1, true);
                item.setItemMeta(meta);
            }
            return item;
        });
        leveling_settings.put(2, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.SHARPNESS, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        });
        leveling_settings.put(3, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.SHARPNESS, 3, true);
                item.setItemMeta(meta);
            }
            return item;
        });
        leveling_settings.put(4, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.SHARPNESS, 4, true);
                item.setItemMeta(meta);
            }
            return item;
        });
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.SHARPNESS, 1, true);
                item.setItemMeta(meta);
            }
            return item;
        };
    }

    public static HashMap<FishingRarity, Integer> getRarityPercentages(int level) {
        HashMap<FishingRarity, Integer> percentages = new HashMap<>();
        if (level== 2) {
            percentages.put(FishingRarity.Rare, 350);
            percentages.put(FishingRarity.SuperRare, 180);
            percentages.put(FishingRarity.UltraRare, 20);
            percentages.put(FishingRarity.CreamRare, 3);
            percentages.put(FishingRarity.PickaxeRare, 2);
            return percentages;
        }
        if (level == 3) {
            percentages.put(FishingRarity.Rare, 400);
            percentages.put(FishingRarity.SuperRare, 190);
            percentages.put(FishingRarity.UltraRare, 50);
            percentages.put(FishingRarity.CreamRare, 3);
            percentages.put(FishingRarity.PickaxeRare, 2);
            return percentages;
        }
        if (level == 4) {
            percentages.put(FishingRarity.Rare, 450);
            percentages.put(FishingRarity.SuperRare, 200);
            percentages.put(FishingRarity.UltraRare, 70);
            percentages.put(FishingRarity.CreamRare, 5);
            percentages.put(FishingRarity.PickaxeRare, 4);
            return percentages;
        }

        percentages.put(FishingRarity.Rare, 300);
        percentages.put(FishingRarity.SuperRare, 150);
        percentages.put(FishingRarity.UltraRare, 10);
        percentages.put(FishingRarity.CreamRare, 500);
        percentages.put(FishingRarity.PickaxeRare, 500);
        return percentages;
    }

}
