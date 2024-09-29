package space.yurisi.universecorev2.item.pickaxe;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.LevellingCustomItem;

public class FishingPickaxe extends CustomItem {

    public static final String id = "fishing_pickaxe";

    public FishingPickaxe(){
        super(
                id,
                "§d恒星の輝きを放つピッケル",
                ItemStack.of(Material.DIAMOND_PICKAXE)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.EFFICIENCY, 4, true);
                meta.addEnchant(Enchantment.UNBREAKING, 2, true);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
