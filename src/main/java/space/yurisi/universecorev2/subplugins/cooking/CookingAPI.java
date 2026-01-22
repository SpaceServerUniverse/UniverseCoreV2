package space.yurisi.universecorev2.subplugins.cooking;

import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.CookingRecipe;
import space.yurisi.universecorev2.database.repositories.CookingRecipeRepository;
import space.yurisi.universecorev2.exception.CookingRecipeNotFoundException;
import space.yurisi.universecorev2.subplugins.cooking.utils.RecipeFlagOps;

import java.util.HashMap;
import java.util.UUID;

public final class CookingAPI {

    private static CookingAPI instance = null;

    private final HashMap<UUID, byte[]> cachedPlayerData = new HashMap<>();

    public CookingAPI(Cooking cooking){
        instance = this;
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

    public void cacheRepositoryData(UUID uuid){
        CookingRecipeRepository repository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(CookingRecipeRepository.class);
        CookingRecipe recipe;
        try {
            recipe = repository.getRecipeFlagsFromPlayer(uuid.toString());
        } catch (CookingRecipeNotFoundException e) {
            recipe = repository.createCookingRecipe(uuid.toString(), RecipeFlagOps.empty().toByteArray());
        }
        if(cachedPlayerData.containsKey(uuid)){
            cachedPlayerData.replace(uuid, recipe.getRecipe());
            return;
        }
        cachedPlayerData.put(uuid, recipe.getRecipe());
    }

    public void saveRepositoryData(UUID uuid){
        if(!cachedPlayerData.containsKey(uuid)) return;
        CookingRecipeRepository repository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(CookingRecipeRepository.class);
        try {
            CookingRecipe recipe = repository.getRecipeFlagsFromPlayer(uuid.toString());
            recipe.setRecipe(cachedPlayerData.get(uuid));
            cachedPlayerData.remove(uuid);
            repository.updateRecipeFlags(recipe);
        } catch (CookingRecipeNotFoundException e) {
            repository.createCookingRecipe(uuid.toString(), cachedPlayerData.get(uuid));
        }
    }

    public void close(){
        for(UUID uuid : cachedPlayerData.keySet()){
            saveRepositoryData(uuid);
        }
    }

    /**
     * @param uuid UUID
     * @param flagId int
     * @throws IllegalStateException if player data is not cached.
     * @return boolean
     */
    public boolean hasRecipe(UUID uuid, int flagId){
        if(!cachedPlayerData.containsKey(uuid)){
            throw new IllegalStateException("Player data is not cached for UUID: " + uuid.toString());
        }
        byte[] recipeBytes = cachedPlayerData.get(uuid);
        return RecipeFlagOps.contains(RecipeFlagOps.fromBytes(recipeBytes), flagId);
    }

    /**
     * @param uuid UUID
     * @param flagId int
     * @throws IllegalStateException if player data is not cached.
     */
    public void addRecipe(UUID uuid, int flagId){
        if(!cachedPlayerData.containsKey(uuid)){
            throw new IllegalStateException("Player data is not cached for UUID: " + uuid.toString());
        }
        byte[] recipeBytes = cachedPlayerData.get(uuid);
        var bitset = RecipeFlagOps.fromBytes(recipeBytes);
        RecipeFlagOps.add(bitset, flagId);
        cachedPlayerData.put(uuid, bitset.toByteArray());
    }

    /**
     * @param uuid UUID
     * @param flagId int
     * @throws IllegalStateException if player data is not cached.
     */
    public void removeRecipe(UUID uuid, int flagId){
        if(!cachedPlayerData.containsKey(uuid)){
            throw new IllegalStateException("Player data is not cached for UUID: " + uuid.toString());
        }
        byte[] recipeBytes = cachedPlayerData.get(uuid);
        var bitset = RecipeFlagOps.fromBytes(recipeBytes);
        RecipeFlagOps.remove(bitset, flagId);
        cachedPlayerData.put(uuid, bitset.toByteArray());
    }
}
