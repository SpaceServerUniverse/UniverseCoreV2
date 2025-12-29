package space.yurisi.universecorev2.item.cooking;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;

public abstract class FoodBaseItem extends CookingItem {

    protected FoodBaseItem(String id, String name) {
        super(id, name, ItemStack.of(Material.COBBLESTONE));
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
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.COOKING_ITEM), PersistentDataType.STRING, UniverseItemKeyString.COOKING_ITEM);
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.FOOD_BASE), PersistentDataType.STRING, this.getId());
            item.setItemMeta(meta);
        }
        return item;
    }
}
