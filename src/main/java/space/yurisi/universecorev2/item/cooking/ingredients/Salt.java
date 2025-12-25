package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class Salt extends IngredientItem {

    public static final String id = "salt";

    public Salt() {
        super(
                Salt.id,
                "塩",
                ItemStack.of(Material.SUGAR)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, Salt.id);
                container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.INGREDIENT), PersistentDataType.STRING, Salt.id);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
