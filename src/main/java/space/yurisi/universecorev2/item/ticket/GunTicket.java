package space.yurisi.universecorev2.item.ticket;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.CustomItem;

public class GunTicket extends CustomItem {

    public static final String id = "gun_ticket";

    public GunTicket() {
        super(
                id,
                "§e§l銃チケット",
                ItemStack.of(Material.PAPER)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_TICKET), PersistentDataType.BOOLEAN, true);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
