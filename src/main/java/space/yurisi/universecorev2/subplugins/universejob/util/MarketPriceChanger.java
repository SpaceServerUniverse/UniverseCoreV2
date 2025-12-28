package space.yurisi.universecorev2.subplugins.universejob.util;

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
import space.yurisi.universecorev2.subplugins.universejob.UniverseJob;
import space.yurisi.universecorev2.subplugins.universejob.constants.JobType;
import space.yurisi.universecorev2.subplugins.universejob.constants.OreList;
import space.yurisi.universecorev2.subplugins.universejob.constants.StoneList;
import space.yurisi.universecorev2.subplugins.universejob.constants.WoodenList;

public class MarketPriceChanger {

    public static long sellerPriceChanger(String sellerUUID, ItemStack itemStack, long basePrice) {
        JobType sellerJobType;
        if(basePrice == 0){
            return 0;
        }
        NamespacedKey key = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);

        try{
            sellerJobType = JobType.getJobTypeFromID(UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(JobRepository.class).getJobIDFromUUID(sellerUUID));
            switch (sellerJobType){
                case JobType.LUMBERJACK:
                    if(WoodenList.WOODEN_MATERIALS.contains(itemStack.getType())){
                        basePrice = ceilMultiply(basePrice, 11, 10);
                    }
                    break;

                case JobType.MINER:
                    if(OreList.ORE_MATERIALS.contains(itemStack.getType()) ||
                            StoneList.STONE_MATERIALS.contains(itemStack.getType()))
                    {
                        basePrice = ceilMultiply(basePrice, 11, 10);
                    }

                    break;

                case JobType.CHEF:
                    if(itemStack.getType().isEdible()){
                        basePrice = ceilMultiply(basePrice, 11, 10);
                    }
                    break;

                case JobType.GLASSBLOWER:
                    if(itemStack.getType().name().contains("GLASS")){
                        basePrice = ceilMultiply(basePrice, 11, 10);
                    }
                    break;

                case JobType.CARPENTER:
                    if(itemStack.getType().equals(Material.DIRT) || itemStack.getType().equals(Material.SCAFFOLDING)){
                        basePrice = ceilMultiply(basePrice, 12, 10);
                    }
                    break;

                case JobType.RETAIL_WORKER:
                    String itemName = itemStack.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    if(itemName != null && itemName.contains("solar_system")){
                        basePrice = ceilMultiply(basePrice, 105, 100);
                    }
                    break;

                default:
                    break;
            }
        } catch (JobTypeNotFoundException ignored){}

        return basePrice;
    }

    public static long purchaserPriceChanger(Player purchaser, ItemStack itemStack, long basePrice) {
        JobType purchaserJobType;
        if(basePrice == 0){
            return 0;
        }

        try{
            purchaserJobType = UniverseJob.getInstance().getPlayerJobManager().getPlayerJobType(purchaser);
            switch (purchaserJobType){

                case BUILDER:
                    if(itemStack.getType().name().contains("CONCRETE")){
                        // 購入者割引は切り捨て
                        basePrice = floorMultiply(basePrice, 9, 10);
                    }else if(StoneList.STONE_MATERIALS.contains(itemStack.getType())){
                        basePrice = floorMultiply(basePrice, 9, 10);
                    }else if(WoodenList.WOODEN_MATERIALS.contains(itemStack.getType())){
                        basePrice = floorMultiply(basePrice, 9, 10);
                    }
                    break;

                case ENGINEER:
                    if(itemStack.getType().name().contains("REDSTONE") || itemStack.getType().name().contains("REPEATER") || itemStack.getType().name().contains("COMPARATOR")){
                        basePrice = floorMultiply(basePrice, 9, 10);
                    }
                    break;
                default:
                    break;
            }
        } catch (JobTypeNotFoundException ignored){}

        return basePrice;
    }

    // Math.ceil(double)だと.0がでないから、小数点でない価格の切り上げのとき1増えちゃうので独自実装
    private static long ceilMultiply(long value, long mul, long div){
        return (value * mul + (div - 1)) / div;
    }

    // こっちは切り捨て
    private static long floorMultiply(long value, long mul, long div){
        return (value * mul) / div;
    }
}
