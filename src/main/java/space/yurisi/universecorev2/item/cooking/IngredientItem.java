package space.yurisi.universecorev2.item.cooking;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;

public abstract class IngredientItem extends CookingItem {

    protected IngredientItem(String id, String name, ItemStack baseItem) {
        super(id, name, baseItem);
    }

    @Override
    protected void registerItemFunction() {
        default_setting = this::registerItemFunctionBase;
    }

    protected ItemStack registerItemFunctionBase(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, this.getId());
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.COOKING_ITEM), PersistentDataType.STRING, this.getId());
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.INGREDIENT), PersistentDataType.STRING, this.getId());
            item.setItemMeta(meta);
        }
        return item;
    }
}
