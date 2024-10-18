package space.yurisi.universecorev2.item.book;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.CustomItem;

public class MainMenuBook extends CustomItem {

    public static final String id = "main_menu_book";

    public MainMenuBook() {
        super(
                id,
                "§eメインメニュー",
                // 本来の用途はレシピの限定解放だが, あくまでミニゲーム用で生活鯖運用ではただの緑の本
                // https://ja.minecraft.wiki/w/%E7%9F%A5%E6%81%B5%E3%81%AE%E6%9C%AC
                ItemStack.of(Material.KNOWLEDGE_BOOK)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
