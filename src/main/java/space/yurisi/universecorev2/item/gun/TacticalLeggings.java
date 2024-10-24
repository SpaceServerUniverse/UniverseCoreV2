package space.yurisi.universecorev2.item.gun;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.CustomItem;

import java.awt.*;
import java.util.List;

public class TacticalLeggings extends CustomItem {

    public static final String id = "tactical_leggings";

    public TacticalLeggings() {
        super(
                id,
                "§dタクティカルレギンス",
                ItemStack.of(Material.LEATHER_LEGGINGS)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.UNBREAKING, 2, true);
                List<Component> lore = List.of(
                        Component.text("§7マガジンを取り出しやすくした戦闘用のレギンス"),
                        Component.text("§7装備することでリロード速度が上昇する")
                );
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
