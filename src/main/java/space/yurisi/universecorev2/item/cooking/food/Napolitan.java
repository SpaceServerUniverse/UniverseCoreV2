package space.yurisi.universecorev2.item.cooking.food;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.Edible;
import space.yurisi.universecorev2.item.cooking.FoodBaseItem;
import space.yurisi.universecorev2.item.cooking.FoodItem;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.item.cooking.FurnaceResult;
import space.yurisi.universecorev2.item.cooking.foodbase.NapolitanBase;

public final class Napolitan extends FoodItem implements Edible, FurnaceResult {

    public static final String id = "napolitan";

    public Napolitan() {
        super(
                Napolitan.id,
                "ナポリタン",
                ItemStack.of(Material.BEETROOT_SOUP),
                10,
                15.0f
        );
    }

    @Override
    public void onEat(Player player) {
        player.sendActionBar(Component.text("§bナポリタンを食べた！"));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60 * 20, 0, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 45 * 20, 0, true));
    }

    @Override
    public FoodBaseItem getFurnaceBaseItem() {
        return (FoodBaseItem) UniverseItem.getItem(NapolitanBase.id);
    }
}
