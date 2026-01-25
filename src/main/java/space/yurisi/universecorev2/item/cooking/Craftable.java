package space.yurisi.universecorev2.item.cooking;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.cooking.entry.RecipeEntryItemStack;
import space.yurisi.universecorev2.subplugins.cooking.CookingAPI;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public interface Craftable {

    /**
     * Returns the recipe matrix
     *
     * @return HashMap<Integer, RecipeEntryItemStack>
     */
    @NotNull
    HashMap<Integer, RecipeEntryItemStack> getRecipe();

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
        ItemStack[] providedRecipe = new ItemStack[9];
        for (int i = 0; i <= 8; i++) {
            ItemStack item = recipeToCheck[i];
            if (item == null) {
                providedRecipe[i] = null;
                continue;
            }
            providedRecipe[i] = item.clone();
        }
        HashMap<Integer, RecipeEntryItemStack> recipe = this.getRecipe();
        NamespacedKey key = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.COOKING_ITEM);
        if(this.isShaped()){
            for(int j = 0; j <= 8; j++){
                if(recipe.get(j) == null && providedRecipe[j] == null) continue;
                if(recipe.get(j) == null || providedRecipe[j] == null) return false;
                ItemStack requiredItem = recipe.get(j).getItemStack();
                ItemStack providedItem = providedRecipe[j];
                PersistentDataContainer requiredContainer = requiredItem.getItemMeta().getPersistentDataContainer();
                PersistentDataContainer providedContainer = providedItem.getItemMeta().getPersistentDataContainer();
                if(requiredContainer.has(key) && !providedContainer.has(key)) return false;
                if(!requiredContainer.has(key) && providedContainer.has(key)) return false;
                if(requiredContainer.has(key) && providedContainer.has(key)){
                    if(!Objects.equals(requiredContainer.get(key, PersistentDataType.STRING), providedContainer.get(key, PersistentDataType.STRING))) return false;
                }else{
                    if(!requiredItem.isSimilar(providedItem)) return false;
                }
            }
        }else{
            for(int k = 0; k <= 8; k++){
                if(recipe.get(k) == null) continue;
                ItemStack requiredItem = recipe.get(k).getItemStack();
                boolean found = false;
                for(int l = 0; l <= 8; l++){
                    ItemStack providedItem = providedRecipe[l];
                    if(providedItem == null) continue;
                    PersistentDataContainer requiredContainer = requiredItem.getItemMeta().getPersistentDataContainer();
                    PersistentDataContainer providedContainer = providedItem.getItemMeta().getPersistentDataContainer();
                    if(!Objects.equals(requiredContainer.get(key, PersistentDataType.STRING), providedContainer.get(key, PersistentDataType.STRING))) continue;
                    found = true;
                    providedRecipe[l] = null;
                    break;
                }
                if(!found) return false;
            }
            for (ItemStack provided: providedRecipe) {
                if (provided != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
