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

public abstract class CustomItem {

    private final String id;
    private final String name;
    private final int min_level;
    private final int max_level;
    private final ItemStack base_item;

    public Map<Integer, Function<ItemStack, ItemStack>> executes = new HashMap<>();

    // コンストラクタで必須のフィールドを強制的に設定
    public CustomItem(String id, String name, int min_level, int max_level, ItemStack base_item) {
        this.id = id;
        this.name = name;
        this.min_level = min_level;
        this.max_level = max_level;
        this.base_item = base_item;
        registerBaseItemFunctions();
    }


    public String getId(){
        return this.id;
    }

    /**
     * 名前を取得する
     */
    public String getName() {
        return name;
    }

    /**
     * ベースアイテムを取得する
     */
    public ItemStack getBaseItem() {
        return base_item;
    }

    public ItemStack getItem(){
        ItemStack item = base_item.clone();
        ItemMeta meta = base_item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, getId());
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getItem(int level){
        ItemStack item = base_item.clone();
        ItemMeta meta = base_item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, getId());
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.LEVEL), PersistentDataType.INTEGER, level);
        Component view_name = Component.text(name+" §bLv."+level);
        meta.displayName(view_name);
        item.setItemMeta(meta);
        return item;
    }


    public Boolean isLevelingItem(){
        return !(min_level == max_level);
    }

    /**
     *  最小レベルを取得する
     */
    public int getMinLevel() {
        return min_level;
    }

    /**
     *  最大レベルを取得する
     */

    public int getMaxLevel() {
        return max_level;
    }

    /**
     * レベルに応じたアイテムを取得する
     */
    public ItemStack getItemFromLevel(int level) throws CustomItemLevelNotFoundException {
        if(!executes.containsKey(level)){
            throw new CustomItemLevelNotFoundException("レベルが登録されていません。");
        }
        return executes.get(level).apply(getItem(level));
    }

    /**
     * レベル別の無名関数を登録するメソッドをサブクラスで実装
     */

    public abstract void registerBaseItemFunctions();
}
