package space.yurisi.universecorev2.item.cooking.food;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.cooking.FoodItem;
import org.bukkit.entity.Player;

public final class Napolitan extends FoodItem {

    public static final String id = "napolitan";

    public Napolitan() {
        super(
                Napolitan.id,
                "ナポリタン",
                ItemStack.of(Material.BEETROOT_SOUP),
                10,
                15.0f
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, Napolitan.id);
                container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.FOOD), PersistentDataType.STRING, Napolitan.id);
                item.setItemMeta(meta);
            }
            return item;
        };
    }

    public void onEat(Player player) {

    }
}
