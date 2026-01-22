package space.yurisi.universecorev2.item.cooking.food;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.Edible;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.FoodItem;
import space.yurisi.universecorev2.item.cooking.FurnaceResult;

public final class GoheiMochi extends FoodItem implements Edible, FurnaceResult {

    public static final String id = "gohei_mochi";

    public GoheiMochi() {
        super(
                GoheiMochi.id,
                "五平餅",
                ItemStack.of(Material.DRIED_KELP),
                8,
                12.0f
        );
    }

    @Override
    public void onEat(Player player) {
        player.sendActionBar(Component.text("§b五平餅を食べた！"));
        player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.HASTE, 30 * 20, 1, false));
    }

    @Override
    public FoodBaseItem getFurnaceBaseItem() {
        return (FoodBaseItem) UniverseItem.getItem(GoheiMochi.id);
    }
}
