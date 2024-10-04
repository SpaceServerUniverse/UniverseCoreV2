package space.yurisi.universecorev2.subplugins.chestshop.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.kyori.adventure.translation.Translator;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;

public class ItemUtils {
    public static ItemStack deserialize(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("JSON文字列がnullまたは空です");
        }
        Gson gson = new Gson();
        Map<String, Object> itemMap = gson.fromJson(json, Map.class);
        return ItemStack.deserialize(itemMap);
    }

    public static String name(ItemStack item) {
        if (item.getItemMeta().hasDisplayName()) {
            return Objects.requireNonNull(item.getItemMeta().displayName()).toString();
        } else {
            return item.getType().name().replace("_", " ").toLowerCase();
        }
    }
}
