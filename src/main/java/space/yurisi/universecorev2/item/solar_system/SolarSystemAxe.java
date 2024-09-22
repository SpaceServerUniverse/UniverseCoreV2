package space.yurisi.universecorev2.item.solar_system;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.LevellingCustomItem;

public final class SolarSystemAxe extends LevellingCustomItem {

    public static final String id = "solar_system_axe";

    public SolarSystemAxe() {
        super(
                id,
                "§8§lSaturn's Glaive",
                1,
                5,
                ItemStack.of(Material.DIAMOND_AXE)
        );
        registerLevelingItemFunctions();
    }

    @Override
    protected void registerLevelingItemFunctions() {
        leveling_settings.put(1, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.EFFICIENCY, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        });

        leveling_settings.put(2, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
                item.setItemMeta(meta);
            }
            return item;
        });

        leveling_settings.put(3, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.EFFICIENCY, 2, true);
                meta.addEnchant(Enchantment.SHARPNESS, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        });

        leveling_settings.put(4, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
                meta.addEnchant(Enchantment.SHARPNESS, 2, true);
                meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                item.setItemMeta(meta);
            }
            return item;
        });

        leveling_settings.put(5, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.EFFICIENCY, 4, true);
                meta.addEnchant(Enchantment.SHARPNESS, 4, true);
                meta.addEnchant(Enchantment.UNBREAKING, 2, true);
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
                meta.addEnchant(Enchantment.EFFICIENCY, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
