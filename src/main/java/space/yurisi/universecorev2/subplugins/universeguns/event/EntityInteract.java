package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import space.yurisi.universecorev2.subplugins.universeguns.constants.ShopType;
import space.yurisi.universecorev2.subplugins.universeguns.entity.GunShopClerk;
import space.yurisi.universecorev2.subplugins.universeguns.menu.shop_menu.*;

public class EntityInteract implements Listener{

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        ShopType shopType = GunShopClerk.getShopType(event.getRightClicked());
        if(shopType == null) {
            return;
        }
        Player player = event.getPlayer();
        event.setCancelled(true);

        switch (shopType) {
            case HGShop:
                HandGunShopMenu handGunShopMenu = new HandGunShopMenu();
                handGunShopMenu.sendMenu(player);
                break;
            case SMGShop:
                SubMachineGunShopMenu subMachineGunShopMenu = new SubMachineGunShopMenu();
                subMachineGunShopMenu.sendMenu(player);
                break;
            case SGShop:
                ShotGunShopMenu shotGunShopMenu = new ShotGunShopMenu();
                shotGunShopMenu.sendMenu(player);
                break;
            case ARShop:
                AssultRifleShopMenu assultRifleShopMenu = new AssultRifleShopMenu();
                assultRifleShopMenu.sendMenu(player);
                break;
            case SRShop:
                SniperRifleShopMenu sniperRifleShopMenu = new SniperRifleShopMenu();
                sniperRifleShopMenu.sendMenu(player);
                break;
            case LMGShop:
                break;
            case EXShop:
                ExplosiveShopMenu explosiveShopMenu = new ExplosiveShopMenu();
                explosiveShopMenu.sendMenu(player);
                break;
            case EQUIPMENTShop:
                break;
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            ShopType shopType = GunShopClerk.getShopType(event.getEntity());
            if(shopType != null) {
                event.setCancelled(true);
            }
        }
    }
}
