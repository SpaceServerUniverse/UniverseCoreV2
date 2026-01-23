package space.yurisi.universecorev2.item.ticket;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.subplugins.gacha.Gacha;
import space.yurisi.universecorev2.subplugins.gacha.constrants.GachaType;

import java.util.List;

public class LoseTicket extends CustomItem {

    public static final String id = "lose_ticket";

    public LoseTicket() {
        super(
                id,
                "§d§lハズレ券",
                ItemStack.of(Material.PAPER)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                item.setItemMeta(meta);
            }
            return item;
        };
    }

    public void setLoseTicketType(ItemStack itemStack, GachaType type){
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.LOSE_TICKET_TYPE), PersistentDataType.STRING, type.getId());
            List<Component> lore = List.of(
                    Component.text("§7100枚集めると" + type.getTypeName() + "の好きなURと交換できます！")
            );
            meta.lore(lore);
            itemStack.setItemMeta(meta);
        }
    }
}
