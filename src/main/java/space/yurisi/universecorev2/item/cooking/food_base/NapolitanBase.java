package space.yurisi.universecorev2.item.cooking.food_base;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.food.Napolitan;

public final class NapolitanBase extends FoodBaseItem {

    public static final String id = "napolitan_base";

    public NapolitanBase(){
        super(
                NapolitanBase.id,
                "§c§lナポリタンの素",
                ItemStack.of(Material.RABBIT_STEW)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, NapolitanBase.id);
                container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.FOOD_BASE), PersistentDataType.STRING, NapolitanBase.id);
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
