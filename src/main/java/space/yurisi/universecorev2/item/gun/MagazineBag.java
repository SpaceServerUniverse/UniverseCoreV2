package space.yurisi.universecorev2.item.gun;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.CustomItem;

public class MagazineBag extends CustomItem {

    public static final String id = "magazine_bag";

    public MagazineBag() {
        super(
                id,
                "§dマガジンバッグ",
                ItemStack.of(Material.IRON_NUGGET)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(1);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
