package space.yurisi.universecorev2.subplugins.birthdaycard.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public class PageJsonUtils {
    private static final Gson gson = new Gson();

    public static List<Component> deserializePageJson(String pageJson) {
        if (pageJson == null || pageJson.isEmpty()) {
            throw new IllegalArgumentException("JSON文字列がnullまたは空です");
        }
        List<String> deserializedStrings = gson.fromJson(pageJson, new TypeToken<List<String>>() {
        }.getType());
        List<Component> components = new ArrayList<>();
        for (String str : deserializedStrings) {
            components.add(GsonComponentSerializer.gson().deserialize(str));
        }
        return components;
    }

    public static String serializePageJson(List<Component> pageComponents) {
        List<String> pagesAsStrings = new ArrayList<>();

        for (Component component : pageComponents) {
            pagesAsStrings.add(GsonComponentSerializer.gson().serialize(component));
        }
        return gson.toJson(pagesAsStrings);
    }

}
