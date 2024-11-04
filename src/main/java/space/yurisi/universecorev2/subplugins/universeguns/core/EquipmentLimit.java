package space.yurisi.universecorev2.subplugins.universeguns.core;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;
import space.yurisi.universecorev2.utils.Message;

import java.util.Objects;

public class EquipmentLimit {

    private boolean[] checkEquipmentLimit(Player player){
        int primaryLimit = 1;
        int secondaryLimit = 1;
        ItemStack chestplate = player.getInventory().getChestplate();
        if(chestplate != null && chestplate.hasItemMeta()){
            ItemMeta meta = chestplate.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
            if(container.has(itemKey, PersistentDataType.STRING) && Objects.equals(container.get(itemKey, PersistentDataType.STRING), "tactical_vest")){
                secondaryLimit = 2;
            }
        }
        int[] hotbarCount = scanHotbar(player);
        boolean[] result = new boolean[2];
        // 超えてたらfalse
        result[0] = hotbarCount[0] <= primaryLimit;
        result[1] = hotbarCount[1] <= secondaryLimit;
        return result;
    }

    public int[] scanHotbar(Player player){
        int primaryCount = 0;
        int secondaryCount = 0;

        for (int i = 0; i < 9; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if(item == null){
                continue;
            }
            if(item.getType().isAir()){
                continue;
            }
            if (!item.hasItemMeta()) {
                continue;
            }

            Gun gun = Gun.getGun(item);
            if (gun == null) {
                continue;
            }

            if (gun.getEquipmentType() == GunType.PRIMARY) {
                primaryCount++;
            } else if (gun.getEquipmentType() == GunType.SECONDARY) {
                secondaryCount++;
            }
        }

        return new int[]{primaryCount, secondaryCount};
    }

    public boolean debuffEquipmentLimit(Player player){
        boolean[] result = checkEquipmentLimit(player);
        if(!result[0] || !result[1]){
            setEquipmentEffect(player, true);
            if(!result[0]){
                Message.sendWarningMessage(player, "[武器AI]", "プライマリの所持制限を超えています。");
            }
            if(!result[1]){
                Message.sendWarningMessage(player, "[武器AI]", "セカンダリの所持制限を超えています。");
            }
            return true;
        }
        setEquipmentEffect(player, false);
        return false;
    }

    public void setEquipmentEffect(Player player, Boolean isOver){
        if(isOver){
            player.addPotionEffect(PotionEffectType.SLOWNESS.createEffect(1000000, 10));
            player.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(1000000, 10));
        }else{
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}
