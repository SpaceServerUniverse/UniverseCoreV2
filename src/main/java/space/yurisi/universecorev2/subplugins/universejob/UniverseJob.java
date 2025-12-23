package space.yurisi.universecorev2.subplugins.universejob;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.database.repositories.JobRepository;
import space.yurisi.universecorev2.exception.JobTypeNotFoundException;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universejob.command.JobCommand;
import space.yurisi.universecorev2.subplugins.universejob.constants.JobType;
import space.yurisi.universecorev2.subplugins.universejob.manager.EventManager;
import space.yurisi.universecorev2.subplugins.universejob.manager.PlayerJobManager;

import java.util.List;

public class UniverseJob implements SubPlugin {

    private static UniverseJob instance;

    private JobRepository jobRepository;

    private UniverseCoreAPIConnector connector;

    private PlayerJobManager playerJobManager;

    public static UniverseJob getInstance() {return instance;}

    public JobRepository getJobRepository() { return jobRepository; }

    public UniverseCoreAPIConnector getConnector() { return connector; }

    public PlayerJobManager getPlayerJobManager() { return playerJobManager; }

    public void onEnable(UniverseCoreV2 core) {
        instance = this;
        this.connector = new UniverseCoreAPIConnector();
        this.jobRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(JobRepository.class);
        playerJobManager = new PlayerJobManager(this);
        core.getCommand("job").setExecutor(new JobCommand());
        new UniverseJobAPI(playerJobManager, connector);
        new EventManager(core, this);
    }

    public void onDisable() {

    }

    public static List<Long> marketPriceChanger(String sellerUUID, Player purchaser, ItemStack item, long price){
        JobType sellerJobType;
        long sellerPrice = price;
        long purchaserPrice = price;
        NamespacedKey key = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        try{
            sellerJobType = JobType.getJobTypeFromID(UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(JobRepository.class).getJobIDFromUUID(sellerUUID));
            switch (sellerJobType){
                case JobType.LUMBERJACK:
                    if(item.getType().name().contains("LOG") || item.getType().name().contains("_WOOD") || item.getType().name().contains("PLANKS")){
                        sellerPrice = (long)(sellerPrice * 1.1);
                    }
                    break;
                case JobType.MINER:
                    if(item.getType().name().contains("ORE"))
                    {
                        sellerPrice = (long)(sellerPrice * 1.1);
                    }else if(item.equals(ItemStack.of(Material.DIAMOND)) || item.equals(ItemStack.of(Material.EMERALD)) ||
                            item.equals(ItemStack.of(Material.STONE)) || item.equals(ItemStack.of(Material.COBBLESTONE)))
                    {
                        sellerPrice = (long)(sellerPrice * 1.1);
                    }
                    break;
                case JobType.CHEF:
                    if(chefList.contains(item)){
                        sellerPrice = (long)(sellerPrice * 1.1);
                    }
                    break;
                case JobType.GLASSBLOWER:
                    if(item.getType().name().contains("GLASS")){
                        sellerPrice = (long)(sellerPrice * 1.1);
                    }
                    break;
                case JobType.CARPENTER:
                    if(item.equals(ItemStack.of(Material.DIRT)) || item.equals(ItemStack.of(Material.SCAFFOLDING))){
                        sellerPrice = (long)(sellerPrice * 1.2);
                    }
                    break;
                case JobType.RETAIL_WORKER:
                    String itemName = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    if(itemName != null && itemName.contains("solar_system")){
                        sellerPrice = (long)(sellerPrice * 1.05);
                    }
                    break;
                default:
                    break;
            }
        } catch (JobTypeNotFoundException ignored){

        }

        try{
            JobType purchaserJobType = UniverseJob.getInstance().getPlayerJobManager().getPlayerJobType(purchaser);
            switch (purchaserJobType){
                case BUILDER:
                    if(item.getType().name().contains("LOG") || item.getType().name().contains("_WOOD") || item.getType().name().contains("CONCRETE")){
                        purchaserPrice = (long)(purchaserPrice * 0.9);
                    }else if(item.equals(ItemStack.of(Material.STONE)) || item.equals(ItemStack.of(Material.COBBLESTONE)) ||
                            item.equals(ItemStack.of(Material.END_STONE))){
                        purchaserPrice = (long)(purchaserPrice * 0.9);
                    }
                    break;
                case ENGINEER:
                    if(item.getType().name().contains("REDSTONE") || item.getType().name().contains("REPEATER") || item.getType().name().contains("COMPARATOR")){
                        purchaserPrice = (long)(purchaserPrice * 0.9);
                    }
                    break;
                default:
                    break;
            }
        } catch (JobTypeNotFoundException ignored){

        }

        return List.of(sellerPrice, purchaserPrice);
    }

    @Override
    public String getName() {
        return "UniverseJob";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    public static List<ItemStack> chefList = List.of(
        ItemStack.of(Material.BEEF),
        ItemStack.of(Material.COOKED_BEEF),
        ItemStack.of(Material.CHICKEN),
        ItemStack.of(Material.COOKED_CHICKEN),
        ItemStack.of(Material.PORKCHOP),
        ItemStack.of(Material.COOKED_PORKCHOP),
        ItemStack.of(Material.MUTTON),
        ItemStack.of(Material.COOKED_MUTTON),
        ItemStack.of(Material.RABBIT),
        ItemStack.of(Material.COOKED_RABBIT),
        ItemStack.of(Material.COD),
        ItemStack.of(Material.COOKED_COD),
        ItemStack.of(Material.SALMON),
        ItemStack.of(Material.COOKED_SALMON),
        ItemStack.of(Material.POTATO),
        ItemStack.of(Material.BAKED_POTATO),
        ItemStack.of(Material.CARROT),
        ItemStack.of(Material.GOLDEN_CARROT),
        ItemStack.of(Material.MELON_SLICE),
        ItemStack.of(Material.MELON_SLICE),
        ItemStack.of(Material.APPLE),
        ItemStack.of(Material.GOLDEN_APPLE),
        ItemStack.of(Material.ENCHANTED_GOLDEN_APPLE),
        ItemStack.of(Material.BEETROOT),
        ItemStack.of(Material.SWEET_BERRIES),
        ItemStack.of(Material.GLOW_BERRIES),
        ItemStack.of(Material.DRIED_KELP),
        ItemStack.of(Material.COOKIE),
        ItemStack.of(Material.BREAD),
        ItemStack.of(Material.CAKE),
        ItemStack.of(Material.PUMPKIN_PIE),
        ItemStack.of(Material.MUSHROOM_STEW),
        ItemStack.of(Material.RABBIT_STEW),
        ItemStack.of(Material.BEETROOT_SOUP),
        ItemStack.of(Material.SUSPICIOUS_STEW)
    );
}
