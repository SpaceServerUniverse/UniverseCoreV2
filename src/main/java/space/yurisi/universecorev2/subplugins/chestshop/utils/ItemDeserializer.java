package space.yurisi.universecorev2.subplugins.chestshop.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemDeserializer {
    public static ItemStack deserialize(String json) {
        Gson gson = new Gson();
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("JSON文字列がnullまたは空です");
        }

        try {
            Map<String, Object> itemMap = gson.fromJson(json, Map.class);
            return ItemStack.deserialize(itemMap);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("JSONの構文が無効です: " + e.getMessage());
        }
    }
}
