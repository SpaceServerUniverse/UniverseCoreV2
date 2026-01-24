package space.yurisi.universecorev2.subplugins.chestshop.event;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.util.Vector;
import org.jspecify.annotations.Nullable;
import space.yurisi.universecorev2.subplugins.containerprotect.event.api.ContainerProtectAPI;

import java.util.Objects;

public class PlayerInteract implements Listener {

    public void onInteract(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(!(e.getClickedBlock() instanceof Sign sign)) return;
        if(!(sign instanceof WallSign wallSign)) return;

        Player player = e.getPlayer();
        Vector e_face = wallSign.getFacing().getDirection();
        Block block = player.getWorld().getBlockAt(e.getClickedBlock().getLocation().add(e_face.multiply(-1)));

        String line1 = sign.getSide(Side.FRONT).line(0).toString();
        String line2 = sign.getSide(Side.FRONT).line(1).toString();
        String line3 = sign.getSide(Side.FRONT).line(2).toString();
        String line4 = sign.getSide(Side.FRONT).line(3).toString();

        if(!Objects.equals(line1, "")) return;
        if(!Objects.equals(line4, "?")) return;
        try {
            int price = Integer.parseInt(line2);
            int amount = Integer.parseInt(line3);
        } catch (NumberFormatException exception) {
            // 書式が違うときは単純に看板とみなすため無視
            return;
        }

        if(!(block instanceof Chest chest)) return;

        InventoryHolder inventory = chest.getBlockInventory().getHolder();

        if(inventory instanceof DoubleChest doubleChest){
            //あしたやる
        }
    }
}
