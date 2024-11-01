package space.yurisi.universecorev2.item.gun;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import space.yurisi.universecorev2.item.CustomItem;

import java.util.List;
import java.util.Random;

public class TacticalVest extends CustomItem {

    public static final String id = "tactical_vest";

    public TacticalVest() {
        super(
                id,
                "§dタクティカルベスト",
                ItemStack.of(Material.LEATHER_CHESTPLATE)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) item.getItemMeta();
            if (leatherMeta != null) {
                leatherMeta.addEnchant(Enchantment.UNBREAKING, 4, true);
                List<Component> lore = List.of(
                        Component.text("§7銃やマガジンを装備するアタッチメントのあるベスト"),
                        Component.text("§7リロード速度上昇とセカンダリの所持枠が§d1§7増加")
//                        Component.text("§7リロード速度上昇")
                );
                leatherMeta.lore(lore);
                Random random = new Random();
                Color colour = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                leatherMeta.setColor(colour);
                item.setItemMeta(leatherMeta);
            }
            return item;
        };
    }
}
