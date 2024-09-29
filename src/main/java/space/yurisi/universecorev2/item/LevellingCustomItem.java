package space.yurisi.universecorev2.item;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class LevellingCustomItem extends CustomItem {

    protected final int min_level;
    protected final int max_level;

    public Map<Integer, Function<ItemStack, ItemStack>> leveling_settings = new HashMap<>();


    public LevellingCustomItem(String id, String name, int min_level, int max_level, ItemStack base_item) {
        super(id, name, base_item);
        this.min_level = min_level;
        this.max_level = max_level;
        registerLevelingItemFunctions();
    }

    /**
     * 最小レベルを取得する
     */
    public int getMinLevel() {
        return min_level;
    }

    /**
     * 最大レベルを取得する
     */

    public int getMaxLevel() {
        return max_level;
    }


    /**
     * レベルに応じたアイテムを取得する
     */
    public ItemStack getItem(int level) throws CustomItemLevelNotFoundException {
        if (!leveling_settings.containsKey(level)) {
            throw new CustomItemLevelNotFoundException("レベルが登録されていません。");
        }
        return leveling_settings.get(level).apply(getLevelBaseItem(level));
    }

    private ItemStack getLevelBaseItem(int level) {
        String view_level = String.valueOf(level);
        if (level == max_level) {
            view_level = "☆MAX☆";
        }
        ItemStack item = getBaseItem().clone();
        ItemMeta meta = getBaseItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, getId());
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.LEVEL), PersistentDataType.INTEGER, level);
        Component view_name = Component.text(name + " §bLv." + view_level);
        meta.displayName(view_name);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * レベル別の無名関数(エンチャント等)を登録するメソッドをサブクラスで実装
     */

    protected abstract void registerLevelingItemFunctions();
}
