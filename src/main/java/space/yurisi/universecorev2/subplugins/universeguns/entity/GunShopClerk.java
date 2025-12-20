package space.yurisi.universecorev2.subplugins.universeguns.entity;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeguns.constants.ShopType;


public class GunShopClerk {

    public void spawnClerk(Location location, ShopType shopType) {
        // Thanks to Charindo
        World world = location.getWorld();
        Villager villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
        villager.setCustomName(getDisplayName(shopType));
        villager.setCustomNameVisible(true);
        villager.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
        villager.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
        villager.getEquipment().setHelmet(new ItemStack(Material.AIR));
        villager.getEquipment().setChestplate(new ItemStack(Material.AIR));
        villager.getEquipment().setLeggings(new ItemStack(Material.AIR));
        villager.getEquipment().setBoots(new ItemStack(Material.AIR));
        villager.getPersistentDataContainer().set(new NamespacedKey(UniverseCoreV2.getInstance(), shopType.getName()),
                PersistentDataType.STRING, shopType.getName());
        villager.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.0D);
        villager.getAttribute(Attribute.ATTACK_KNOCKBACK).setBaseValue(0.0D);
        villager.setCollidable(false);
        villager.setSilent(true);
        villager.setInvulnerable(true);
    }

    public static ShopType getShopType(Entity entity) {
        if(entity instanceof Villager villager) {
            for(ShopType shopType : ShopType.values()) {
                if(villager.getPersistentDataContainer().has(new NamespacedKey(UniverseCoreV2.getInstance(), shopType.getName()), PersistentDataType.STRING)) {
                    return shopType;
                }
            }
        }
        return null;
    }

    public static boolean killClerk(Location location, ShopType shopType) {
        for(Entity entity : location.getWorld().getNearbyEntities(location, 5, 5, 5)) {
            if(entity instanceof Villager villager) {
                if(villager.getPersistentDataContainer().has(new NamespacedKey(UniverseCoreV2.getInstance(), shopType.getName()), PersistentDataType.STRING)) {
                    villager.remove();
                    return true;
                }
            }
        }
        return false;
    }

    private String getDisplayName(ShopType shopType) {
        switch (shopType){
            case HGShop:
                return "ハンドガン";
            case SMGShop:
                return "サブマシンガン";
            case SGShop:
                return "ショットガン";
            case ARShop:
                return "アサルトライフル";
            case SRShop:
                return "スナイパーライフル";
            case LMGShop:
                return "ライトマシンガン";
            case EXShop:
                return "特殊武器";
            case EQUIPMENTShop:
                return "装具類";
            default:
                return null;
        }
    }
}
