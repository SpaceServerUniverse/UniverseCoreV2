package space.yurisi.universecorev2.item.solar_system;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.CustomItem;

public final class SolarSystemChestplate extends CustomItem {

    public static final String id = "solar_system_chestplate";

    public SolarSystemChestplate() {
        super(
                id,
                "§6§lNeptune of Aria",
                ItemStack.of(Material.DIAMOND_CHESTPLATE)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.PROTECTION, 2, true);
                meta.addEnchant(Enchantment.UNBREAKING, 2, true);
                meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
