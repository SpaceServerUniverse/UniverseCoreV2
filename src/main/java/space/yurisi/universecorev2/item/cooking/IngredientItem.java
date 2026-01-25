package space.yurisi.universecorev2.item.cooking;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;

import javax.annotation.Nullable;

public abstract class IngredientItem extends CookingItem {

    protected static final Material EDIBLE = Material.BEETROOT;
    protected static final Material INEDIBLE = Material.SUGAR;
    private final String textureId;

    protected IngredientItem(@NotNull String id, @NotNull String name, @NotNull ItemStack baseItem, @Nullable String textureID) {
        super(id, "【料理専用】" + name, baseItem);
        this.textureId = textureID;
    }

    @Override
    protected void registerItemFunction() {
        default_setting = this::registerItemFunctionBase;
    }

    protected ItemStack registerItemFunctionBase(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME), PersistentDataType.STRING, this.getId());
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.COOKING_ITEM), PersistentDataType.STRING, this.getId());
            container.set(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.INGREDIENT), PersistentDataType.STRING, this.getId());
            if(textureId != null) {
                meta.setItemModel(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.COOKING));
            }

            item.setItemMeta(meta);
        }

        if (textureId != null) {
            CustomModelData modelData = CustomModelData.customModelData().addString(textureId).build();
            item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, modelData);
        }

        return item;
    }
}
