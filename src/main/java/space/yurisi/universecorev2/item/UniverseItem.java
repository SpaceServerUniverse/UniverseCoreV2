package space.yurisi.universecorev2.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.solar_system.SolarSystemAxe;
import space.yurisi.universecorev2.item.solar_system.SolarSystemPickaxe;
import space.yurisi.universecorev2.item.solar_system.SolarSystemShovel;
import space.yurisi.universecorev2.item.solar_system.SolarSystemSword;

import java.util.HashMap;

public class UniverseItem {

    private static HashMap<String, CustomItem> items = new HashMap<>();
    public UniverseItem(){
        register();
    }

    public void register(){
        items.put(SolarSystemSword.id, new SolarSystemSword());
        items.put(SolarSystemShovel.id, new SolarSystemShovel());
        items.put(SolarSystemAxe.id, new SolarSystemAxe());
        items.put(SolarSystemPickaxe.id, new SolarSystemPickaxe());

    }

    public static CustomItem getItem(String id){
        if(!items.containsKey(id)){
            return null;
        }

        return items.get(id);
    }

    public static Boolean isUniverseItem(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return false;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME));
    }

    public static Boolean isLevelingItem(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return false;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.LEVEL));
    }

    public static String[] getItemIds(){
        return items.keySet().toArray(new String[0]);
    }
}
