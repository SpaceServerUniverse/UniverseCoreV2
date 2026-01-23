package space.yurisi.universecorev2.subplugins.cooking;

import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.CookingRecipe;
import space.yurisi.universecorev2.database.repositories.CookingRecipeRepository;
import space.yurisi.universecorev2.exception.CookingRecipeNotFoundException;
import space.yurisi.universecorev2.subplugins.cooking.util.RecipeFlagOps;

import java.util.HashMap;
import java.util.UUID;

public final class CookingAPI {

    private static CookingAPI instance = null;

    private final HashMap<UUID, byte[]> cachedPlayerData = new HashMap<>();

    public CookingAPI(){
        instance = this;
        CookingRecipeRepository repository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(CookingRecipeRepository.class);
        for (CookingRecipe recipe: repository.getAllCookingRecipes()) {
            this.cacheRepositoryData(UUID.fromString(recipe.getPlayerUuid()));
        }
    }

    /**
     * @throws IllegalStateException if CookingAPI is not initialized yet.
     * @return CookingAPI
     */
    public static CookingAPI getInstance(){
        if(instance == null){
            throw new IllegalStateException("CookingAPI is not initialized yet.");
        }
        return instance;
    }

    /**
     * Cache player data from repository.
     *
     * @param uuid UUID
     */
    public void cacheRepositoryData(UUID uuid){
        CookingRecipeRepository repository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(CookingRecipeRepository.class);
        CookingRecipe recipe;
        if(!cachedPlayerData.containsKey(uuid)){
            try {
                recipe = repository.getRecipeFlagsFromUuid(uuid.toString());
            } catch (CookingRecipeNotFoundException e) {
                recipe = repository.createCookingRecipe(uuid.toString(), RecipeFlagOps.empty());
            }
            if(cachedPlayerData.containsKey(uuid)){
                cachedPlayerData.replace(uuid, recipe.getRecipe());
                return;
            }
            cachedPlayerData.put(uuid, recipe.getRecipe());
        }
    }

    /**
     * Save player data to repository.
     *
     * @param uuid UUID
     */
    public void saveRepositoryData(UUID uuid){
        if(!cachedPlayerData.containsKey(uuid)) return;
        CookingRecipeRepository repository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(CookingRecipeRepository.class);
        try {
            CookingRecipe recipe = repository.getRecipeFlagsFromUuid(uuid.toString());
            recipe.setRecipe(cachedPlayerData.get(uuid));
            repository.updateRecipeFlags(recipe);
        } catch (CookingRecipeNotFoundException e) {
            repository.createCookingRecipe(uuid.toString(), cachedPlayerData.get(uuid));
        }
    }

    /**
     * Checks if the player has the recipe.
     *
     * @param uuid UUID
     * @param flagId int
     * @throws IllegalStateException if player data is not cached.
     * @return boolean
     */
    public boolean hasRecipe(UUID uuid, int flagId){
        if(!cachedPlayerData.containsKey(uuid)){
            throw new IllegalStateException("[Cooking] Player data is not cached for UUID: " + uuid.toString());
        }
        byte[] recipeBytes = cachedPlayerData.get(uuid);
        return RecipeFlagOps.contains(recipeBytes, flagId);
    }

    /**
     * Adds a recipe to the player's data.
     *
     * @param uuid UUID
     * @param flagId int
     * @throws IllegalStateException if player data is not cached.
     */
    public void addRecipe(UUID uuid, int flagId){
        if(!cachedPlayerData.containsKey(uuid)){
            throw new IllegalStateException("[Cooking] Player data is not cached for UUID: " + uuid.toString());
        }
        byte[] recipeBytes = RecipeFlagOps.add(cachedPlayerData.get(uuid), flagId);
        cachedPlayerData.replace(uuid, recipeBytes);
    }

    /**
     * Removes a recipe from the player's data.
     *
     * @param uuid UUID
     * @param flagId int
     * @throws IllegalStateException if player data is not cached.
     */
    public void removeRecipe(UUID uuid, int flagId){
        if(!cachedPlayerData.containsKey(uuid)){
            throw new IllegalStateException("[Cooking] Player data is not cached for UUID: " + uuid.toString());
        }
        byte[] recipeBytes = RecipeFlagOps.remove(cachedPlayerData.get(uuid), flagId);
        cachedPlayerData.replace(uuid, recipeBytes);
    }

    /**
     * Gets the recipe bytes of the player.
     *
     * @param uuid UUID
     * @throws IllegalStateException if player data is not cached.
     * @return byte[]
     */
    public byte[] getRecipeBytes(UUID uuid){
        if(!cachedPlayerData.containsKey(uuid)){
            throw new IllegalStateException("[Cooking] Player data is not cached for UUID: " + uuid.toString());
        }
        return cachedPlayerData.get(uuid);
    }

    /**
     * Save all cached player data to repository.
     */
    public void save(){
        for (UUID uuid : cachedPlayerData.keySet()) {
            saveRepositoryData(uuid);
        }
    }
}
