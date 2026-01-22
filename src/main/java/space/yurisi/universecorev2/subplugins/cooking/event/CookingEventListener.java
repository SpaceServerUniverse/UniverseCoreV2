package space.yurisi.universecorev2.subplugins.cooking.event;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.database.models.CookingRecipe;
import space.yurisi.universecorev2.database.repositories.CookingRecipeRepository;
import space.yurisi.universecorev2.exception.CookingRecipeNotFoundException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.*;
import space.yurisi.universecorev2.subplugins.cooking.CookingAPI;
import space.yurisi.universecorev2.subplugins.cooking.utils.RecipeIds;
import space.yurisi.universecorev2.subplugins.cooking.utils.CookingItems;
import space.yurisi.universecorev2.subplugins.cooking.utils.RecipeFlagOps;

import java.util.BitSet;
import java.util.Objects;

public class CookingEventListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        CookingAPI.getInstance().cacheRepositoryData(e.getPlayer().getUniqueId());
    }

    public void onQuit(PlayerQuitEvent e){
        CookingAPI.getInstance().saveRepositoryData(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onFoodEat(PlayerItemConsumeEvent e){
        Player player = e.getPlayer();
        PersistentDataContainer container = e.getItem().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.COOKING_ITEM);
        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        if(!container.has(key)) return;
        CustomItem customItem = UniverseItem.getItem(container.get(itemKey, PersistentDataType.STRING));
        e.setCancelled(true);
        if(customItem instanceof Edible foodItem){
            foodItem.onEat(player);
            UniverseItem.removeItem(player, customItem.getId(), 1);
            int nutrition = player.getFoodLevel();
            float saturation = player.getSaturation();
            int addedNutrition = Math.min(20, nutrition + foodItem.getNutrition());
            //隠し満腹値の最大値は自身の満腹度と一致する(ChatGPT調べ)
            float addedSaturation = Math.min(addedNutrition, saturation + foodItem.getSaturation());
            player.setFoodLevel(addedNutrition);
            player.setSaturation(addedSaturation);
        }
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent e){
        ItemStack[] matrix = e.getInventory().getMatrix();
        if(matrix.length != 9) return;
        FoodBaseItem[] foodBaseItems = CookingItems.getAllCookingItems();
        for(FoodBaseItem foodBaseItem : foodBaseItems){
            if(!(foodBaseItem instanceof Craftable craftable)) continue;
            if(!craftable.canCraftedWith(matrix, e.getView().getPlayer().getUniqueId())) continue;
            CustomItem result = UniverseItem.getItem(foodBaseItem.getId());
            if(result == null) continue;
            e.getInventory().setResult(result.getItem());
        }
    }

    @EventHandler
    public void onFurnace(FurnaceSmeltEvent e){
        ItemStack item = e.getSource();
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.COOKING_ITEM);
        if(!container.has(key)) return;
        CustomItem customItem = UniverseItem.getItem(container.get(key, PersistentDataType.STRING));
        FoodItem[] smeltedItems = CookingItems.getAllFoodItems();
        for(FoodItem smeltedItem : smeltedItems){
            if(!(smeltedItem instanceof FurnaceResult furnaceResult)) continue;
            if(customItem == null) continue;
            if(!Objects.equals(customItem.getId(), furnaceResult.getFurnaceBaseItem().getId())) continue;
            e.setResult(smeltedItem.getItem());
            return;
        }
    }
}
