package space.yurisi.universecorev2.item;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class CustomItem {

    private final String name;
    private final int min_level;
    private final int max_level;
    private final ItemStack base_item;

    public Map<Integer, Function<ItemStack, ItemStack>> executes = new HashMap<>();

    // コンストラクタで必須のフィールドを強制的に設定
    public CustomItem(String name, int min_level, int max_level, ItemStack base_item) {
        this.name = name;
        this.min_level = min_level;
        this.max_level = max_level;
        this.base_item = base_item;
        registerBaseItemFunctions();
    }

    // 名前を取得する
    public String getName() {
        return name;
    }

    // ベースアイテムを取得する
    public ItemStack getBaseItem() {
        return base_item;
    }

    public ItemStack getItem(){
        ItemStack item = base_item.clone();
        ItemMeta meta = base_item.getItemMeta();
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getItem(int level){
        ItemStack item = base_item.clone();
        ItemMeta meta = base_item.getItemMeta();
        Component view_name = Component.text(name+" §bLv."+level);
        meta.displayName(view_name);
        item.setItemMeta(meta);
        return item;
    }

    // 最小レベルを取得する
    public int getMinLevel() {
        return min_level;
    }

    // 最大レベルを取得する
    public int getMaxLevel() {
        return max_level;
    }

    // レベルに応じたアイテムを取得する
    public ItemStack getItemFromLevel(int level) throws CustomItemLevelNotFoundException {
        if(!executes.containsKey(level)){
            throw new CustomItemLevelNotFoundException("レベルが登録されていません。");
        }
        return executes.get(level).apply(getItem(level));
    }

    // 無名関数を登録するメソッドをサブクラスで実装
    public abstract void registerBaseItemFunctions();
}
