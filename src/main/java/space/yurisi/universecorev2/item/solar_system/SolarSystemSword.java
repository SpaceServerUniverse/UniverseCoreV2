package space.yurisi.universecorev2.item.solar_system;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.CustomItem;

public class SolarSystemSword extends CustomItem {

    public SolarSystemSword() {
        super(
                "§e§lノヴァ・クラッシャー",
                1,
                2,
                new ItemStack(Material.DIAMOND_SWORD)
        );
        registerBaseItemFunctions();
    }

    @Override
    public void registerBaseItemFunctions() {
        executes.put(1, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.SHARPNESS, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        });

        executes.put(2, (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.SHARPNESS, 2, true);
                meta.addEnchant(Enchantment.UNBREAKING, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        });
    }
}
