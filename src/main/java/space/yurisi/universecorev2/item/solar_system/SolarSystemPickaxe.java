package space.yurisi.universecorev2.item.solar_system;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.LevellingCustomItem;

public final class SolarSystemPickaxe extends LevellingCustomItem {

    public static final String id = "solar_system_pickaxe";

    public SolarSystemPickaxe() {
        super(
                id,
                "§b§lEarth's Core",
                1,
                5,
                ItemStack.of(Material.DIAMOND_PICKAXE)
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
                meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
                meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
                meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                item.setItemMeta(meta);
            }
            return item;
        });

        leveling_settings.put(4, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
                meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
                meta.addEnchant(Enchantment.UNBREAKING, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        });

        leveling_settings.put(5, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.EFFICIENCY, 4, true);
                meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
                meta.addEnchant(Enchantment.UNBREAKING, 3, true);
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
