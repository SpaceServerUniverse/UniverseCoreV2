package space.yurisi.universecorev2.item;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.fishingrod.FishingRod;
import space.yurisi.universecorev2.item.pickaxe.FishingPickaxe;
import space.yurisi.universecorev2.item.gun.*;
import space.yurisi.universecorev2.item.repair_cream.RepairCream;
import space.yurisi.universecorev2.item.solar_system.*;
import space.yurisi.universecorev2.item.stick.BlockCopyStick;
import space.yurisi.universecorev2.item.ticket.GachaTicket;

import java.util.HashMap;
import java.util.Objects;

public class UniverseItem {

    private static HashMap<String, CustomItem> items = new HashMap<>();
    public UniverseItem(){
        register();
    }

    public void register(){
        items.put(SolarSystemSword.id, new SolarSystemSword());
        items.put(SolarSystemShovel.id, new SolarSystemShovel());
        items.put(SolarSystemAxe.id, new SolarSystemAxe());
        items.put(SolarSystemPickaxe.id, new SolarSystemPickaxe());
        items.put(SolarSystemHead.id, new SolarSystemHead());
        items.put(SolarSystemChestplate.id, new SolarSystemChestplate());
        items.put(SolarSystemLeggings.id, new SolarSystemLeggings());
        items.put(SolarSystemBoots.id, new SolarSystemBoots());
        items.put(GachaTicket.id, new GachaTicket());
        items.put(RepairCream.id, new RepairCream());
        items.put(BlockCopyStick.id, new BlockCopyStick());
        items.put(FishingRod.id, new FishingRod());
        items.put(FishingPickaxe.id, new FishingPickaxe());
        items.put(R4C.id, new R4C());
        items.put(L96A1.id, new L96A1());
        items.put(SMG11.id, new SMG11());
        items.put(RPG.id, new RPG());
        items.put(F2.id, new F2());
        items.put(M870.id, new M870());
        items.put(MagazineBag.id, new MagazineBag());
    }

    public static CustomItem getItem(String id){
        if(!items.containsKey(id)){
            return null;
        }

        return items.get(id);
    }

    public static Boolean isUniverseItem(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return false;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME));
    }

    public static Boolean isUniverseItemFromId(ItemStack itemStack, String id){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return false;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        if(!container.has(key)){
            return false;
        };
        String universeItemName = container.get(key, PersistentDataType.STRING);
        return Objects.equals(id, universeItemName);
    }

    public static Boolean isLevelingItem(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return false;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.LEVEL));
    }


    public static Boolean removeItem(Player player, String item_name){
        PlayerInventory inventory = player.getInventory();
        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();

                if (container.has(itemKey, PersistentDataType.STRING)) {
                    String isGachaTicket = container.get(itemKey, PersistentDataType.STRING);

                    if (Objects.equals(isGachaTicket, item_name)) {
                        if (item.getAmount() > 1) {
                            item.setAmount(item.getAmount() - 1);
                        } else {
                            inventory.remove(item);
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static String[] getItemIds(){
        return items.keySet().toArray(new String[0]);
    }
}
