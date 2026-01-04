package space.yurisi.universecorev2.item.birthday_card;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.CustomItem;

public class BirthdayCard extends CustomItem {

    public static final String id = "birthday_card";


    public BirthdayCard() {
        super(
                id,
                "お誕生日カード (汎用)",
                ItemStack.of(Material.WRITABLE_BOOK)
        );
    }

    public BirthdayCard(Player player) {
        super(
                id,
                "お誕生日カード (" + player.getName() + ")\"",
                ItemStack.of(Material.WRITABLE_BOOK)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> item;
    }

    public ItemStack getItem(Player player){
        ItemStack item = getBaseItem().clone();
        ItemMeta meta = getBaseItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, getId());
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), "BIRTHDAY_DATA"), PersistentDataType.STRING, player.getUniqueId().toString());
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return default_setting.apply(item);
    }

}
