package space.yurisi.universecorev2.item.solar_system;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.CustomItem;

public final class SolarSystemLeggings extends CustomItem {

    public static final String id = "solar_system_leggings";

    public SolarSystemLeggings() {
        super(
                id,
                "§6§lVenus' Embrace",
                ItemStack.of(Material.DIAMOND_LEGGINGS)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.PROTECTION, 2, true);
                meta.addEnchant(Enchantment.UNBREAKING, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
