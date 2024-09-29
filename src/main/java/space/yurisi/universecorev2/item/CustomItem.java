package space.yurisi.universecorev2.item;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;

import java.util.function.Function;

public abstract class CustomItem {

    protected final String id;
    protected final String name;
    protected final ItemStack base_item;

    protected Function<ItemStack, ItemStack> default_setting;


    // コンストラクタで必須のフィールドを強制的に設定
    public CustomItem(String id, String name, ItemStack base_item) {
        this.id = id;
        this.name = name;
        this.base_item = base_item;
        registerItemFunction();
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
        ItemStack item = getBaseItem().clone();
        ItemMeta meta = getBaseItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, getId());
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return default_setting.apply(item);
    }

    /**
     * エンチャント等はここで付与
     */
    protected abstract void registerItemFunction();
}
