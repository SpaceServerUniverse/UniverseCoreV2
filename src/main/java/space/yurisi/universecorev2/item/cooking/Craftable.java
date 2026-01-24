package space.yurisi.universecorev2.item.cooking;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.subplugins.cooking.CookingAPI;

import java.util.UUID;

public interface Craftable {

    /**
     * Returns the recipe matrix
     *
     * @return CookingItem[]
     */
    @NotNull
    CookingItem[] getRecipe();

    /**
     * Checks if the recipe is shaped
     *
     * @return boolean
     */
    boolean isShaped();

    /**
     * Returns the flag ID
     *
     * @return int
     */
    int getFlagId();

    default boolean canCraftWith(ItemStack[] recipeToCheck, UUID uuid) {
        if(recipeToCheck.length != 9){
            return false;
        }
        try{
            if(!CookingAPI.getInstance().hasRecipe(uuid, this.getFlagId())){
                return false;
            }
        }catch (IllegalStateException e){
            UniverseCoreV2.getInstance().getLogger().warning(e.getMessage());
            if(UniverseCoreV2.getInstance().getServer().getPlayer(uuid) instanceof Player player){
                player.sendMessage("§cレシピのデータを正しく取得できませんでした。管理者にお問い合わせください。");
            }
            return false;
        }
        CookingItem[] providedRecipe = new CookingItem[9];
        for (int i = 0; i <= 8; i++) {
            ItemStack item = recipeToCheck[i];
            if (item == null) continue;
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.COOKING_ITEM);
            if(!container.has(key)) return false;
            CustomItem customItem = UniverseItem.getItem(container.get(key, PersistentDataType.STRING));
            if(!(customItem instanceof CookingItem cookingItem)) return false;
            providedRecipe[i] = cookingItem;
        }
        CookingItem[] recipe = this.getRecipe();
        if(this.isShaped()){
            for(int j = 0; j <= 8; j++){
                CookingItem requiredItem = recipe[j];
                CookingItem providedItem = providedRecipe[j];
                if(requiredItem == null && providedItem == null) continue;
                if(requiredItem == null || providedItem == null) return false;
                if(!requiredItem.getId().equals(providedItem.getId())) return false;
            }
        }else{
            for(int k = 0; k <= 8; k++){
                CookingItem requiredItem = recipe[k];
                if(requiredItem == null) continue;
                boolean found = false;
                for(int l = 0; l <= 8; l++){
                    CookingItem providedItem = providedRecipe[l];
                    if(providedItem == null) continue;
                    if(!requiredItem.getId().equals(providedItem.getId())) continue;
                    found = true;
                    providedRecipe[l] = null;
                    break;
                }
                if(!found) return false;
            }
            for (CookingItem provided: providedRecipe) {
                if (provided != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
