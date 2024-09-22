package space.yurisi.universecorev2.subplugins.freemarket.data;

import com.google.gson.Gson;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Objects;

public class JsonConverter {

    public static String ItemStackSerializer(ItemStack itemStack) {
        Gson gson = new Gson();
        ItemStack clone = itemStack.clone();
        clone.setItemMeta(null);
        return gson.toJson(clone.serialize());
    }

    public static String ItemMetaSerializer(ItemStack itemStack) {
        Gson gson = new Gson();
        ItemMeta clone = itemStack.getItemMeta().clone();
        return gson.toJson(clone.serialize());
    }
}
