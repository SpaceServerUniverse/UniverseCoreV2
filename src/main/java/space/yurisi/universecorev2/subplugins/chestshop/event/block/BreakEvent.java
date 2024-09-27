package space.yurisi.universecorev2.subplugins.chestshop.event.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.ChestShop;
import space.yurisi.universecorev2.exception.ChestShopNotFoundException;
import space.yurisi.universecorev2.subplugins.chestshop.utils.SuperMessageHelper;
import space.yurisi.universecorev2.subplugins.containerprotect.event.api.ContainerProtectAPI;

public class BreakEvent implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Location location = block.getLocation();
        ChestShop chestShop;
        if (block.getType() == Material.CHEST) {
            try {
                chestShop = UniverseCoreV2API.getInstance().getDatabaseManager().getChestShopRepository().getChestShopByLocation(location);
            } catch (ChestShopNotFoundException e) {
                return;
            }
            if (!(player.getUniqueId().toString().equals(chestShop.getUuid()))) {
                event.setCancelled(true);
                return;
            }
            UniverseCoreV2API.getInstance().getDatabaseManager().getChestShopRepository().deleteChestShop(chestShop);
            player.sendMessage(SuperMessageHelper.getSuccessMessage("チェストショップを正常に撤去しました"));
        }
        if (block.getBlockData() instanceof WallSign) {
            try {
                chestShop = UniverseCoreV2API.getInstance().getDatabaseManager().getChestShopRepository().getChestShopBySignLocation(location);
            } catch (ChestShopNotFoundException e) {
                return;
            }
            if (!(player.getUniqueId().toString().equals(chestShop.getUuid()))) {
                player.sendMessage(SuperMessageHelper.getErrorMessage("オーナーじゃない為壊すことができません"));
                event.setCancelled(true);
                return;
            }
            World world = Bukkit.getWorld(chestShop.getWorld_name());
            Location chestLocation = new Location(world, chestShop.getMainChest_x(), chestShop.getMainChest_y(), chestShop.getMainChest_z());
            ContainerProtectAPI.getInstance().removeContainerProtect(chestLocation);
            UniverseCoreV2API.getInstance().getDatabaseManager().getChestShopRepository().deleteChestShop(chestShop);
            player.sendMessage(SuperMessageHelper.getSuccessMessage("チェストショップを正常に撤去しました"));
        }
    }
}
