package space.yurisi.universecorev2.item.fish_rod;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.LevellingCustomItem;

public class FishingRod extends LevellingCustomItem {

    public static final String id = "fishing_rodb";

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
    protected void registerItemFunction() {
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
    protected void registerLevelingItemFunctions() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.SHARPNESS, 1, true);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
