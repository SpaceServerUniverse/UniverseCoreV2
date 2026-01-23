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
import space.yurisi.universecorev2.item.book.MainMenuBook;
import space.yurisi.universecorev2.item.book.TutorialBook;
import space.yurisi.universecorev2.item.cooking.food.*;
import space.yurisi.universecorev2.item.cooking.foodbase.*;
import space.yurisi.universecorev2.item.cooking.ingredients.*;
import space.yurisi.universecorev2.item.fishingrod.FishingRod;
import space.yurisi.universecorev2.item.pickaxe.FishingPickaxe;
import space.yurisi.universecorev2.item.gun.*;
import space.yurisi.universecorev2.item.repair_cream.RepairCream;
import space.yurisi.universecorev2.item.solar_system.*;
import space.yurisi.universecorev2.item.stick.BlockCopyStick;
import space.yurisi.universecorev2.item.ticket.GachaTicket;
import space.yurisi.universecorev2.item.ticket.GunTicket;
import space.yurisi.universecorev2.item.ticket.LoseTicket;
import space.yurisi.universecorev2.menu.MainMenu;

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
        items.put(GunTicket.id, new GunTicket());
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
        items.put(M1911.id, new M1911());
        items.put(M134.id, new M134());
        items.put(MagazineBag.id, new MagazineBag());
        items.put(TacticalLeggings.id, new TacticalLeggings());
        items.put(TacticalVest.id, new TacticalVest());
        items.put(MainMenuBook.id, new MainMenuBook());
        items.put(TutorialBook.id, new TutorialBook());
        items.put(LoseTicket.id, new LoseTicket());
        items.put(Carrot.id, new Carrot());
        items.put(GreenPepper.id, new GreenPepper());
        items.put(Pasta.id, new Pasta());
        items.put(RawBeef.id, new RawBeef());
        items.put(RawPork.id, new RawPork());
        items.put(Rice.id, new Rice());
        items.put(Salt.id, new Salt());
        items.put(Shimeji.id, new Shimeji());
        items.put(SoySauce.id, new SoySauce());
        items.put(Sugar.id, new Sugar());
        items.put(Tomato.id, new Tomato());
        items.put(NapolitanBase.id, new NapolitanBase());
        items.put(Napolitan.id, new Napolitan());
        items.put(GoheiMochiBase.id, new GoheiMochiBase());
        items.put(GoheiMochi.id, new GoheiMochi());
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


    public static Boolean removeItem(Player player, String item_name, int amount){
        PlayerInventory inventory = player.getInventory();
        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        int remainingAmount = amount;
        int totalAmount = 0;

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();

                if (container.has(itemKey, PersistentDataType.STRING)) {
                    String isGachaTicket = container.get(itemKey, PersistentDataType.STRING);

                    if (Objects.equals(isGachaTicket, item_name)) {
                        totalAmount += item.getAmount();
                        if(totalAmount >= amount){
                            break;
                        }
                    }
                }
            }
        }

        if (totalAmount < amount) {
            return false;
        }

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();

                if (container.has(itemKey, PersistentDataType.STRING)) {
                    String isGachaTicket = container.get(itemKey, PersistentDataType.STRING);

                    if (Objects.equals(isGachaTicket, item_name)) {
                        int itemAmount = item.getAmount();
                        if (itemAmount <= remainingAmount) {
                            remainingAmount -= itemAmount;
                            item.setAmount(0);
                        } else {
                            item.setAmount(itemAmount - remainingAmount);
                            remainingAmount = 0;
                        }

                        if (remainingAmount == 0) {
                            return true;
                        }
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
