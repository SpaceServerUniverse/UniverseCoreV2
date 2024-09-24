package space.yurisi.universecorev2.subplugins.universeguns.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeguns.item.gun_item.R4C;

import java.util.HashMap;

public class ItemRegister {

    private static HashMap<String, GunItem> items = new HashMap<>();
    public ItemRegister(){
        register();
    }

    public void register() {
        items.put(R4C.id, new R4C());
    }

    public static GunItem getItem(String id){
        if(!items.containsKey(id)){
            return null;
        }

        return items.get(id);
    }
}
