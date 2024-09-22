package space.yurisi.universecorev2.item.ticket;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.CustomItem;

public class GachaTicket extends CustomItem {

    public static final String id = "gacha_ticket";

    public GachaTicket() {
        super(
                id,
                "§e§lガチャチケット",
                ItemStack.of(Material.PAPER));
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GACHA_TICKET), PersistentDataType.BOOLEAN, true);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
