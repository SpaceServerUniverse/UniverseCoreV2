package space.yurisi.universecorev2.item.cooking.ingredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import space.yurisi.universecorev2.item.cooking.IngredientItem;

public final class SoySauce extends IngredientItem {

    public static final String id = "soy_sauce";

    public SoySauce() {
        super(
                id,
                "醤油",
                ItemStack.of(Material.POTION)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            ItemStack basedItem = registerItemFunctionBase(item);
            ItemMeta meta = basedItem.getItemMeta();
            if(meta instanceof PotionMeta potionMeta) {
                potionMeta.setBasePotionType(PotionType.WEAKNESS);
                item.setItemMeta(potionMeta);
            } else {
                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
