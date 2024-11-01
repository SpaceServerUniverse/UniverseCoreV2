package space.yurisi.universecorev2.item.gun;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import space.yurisi.universecorev2.item.CustomItem;

import java.util.List;
import java.util.Random;

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
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.UNBREAKING, 4, true);
                List<Component> lore = List.of(
                        Component.text("§7マガジンを取り出しやすくした戦闘用のレギンス"),
                        Component.text("§7装備することでリロード速度が上昇する")
                );
                meta.lore(lore);
                Random random = new Random();
                Color colour = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                meta.setColor(colour);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
