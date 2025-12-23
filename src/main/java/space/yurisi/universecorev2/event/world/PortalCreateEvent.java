package space.yurisi.universecorev2.event.world;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PortalCreateEvent implements Listener {
    private final Plugin plugin;

    public PortalCreateEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPortal(org.bukkit.event.world.PortalCreateEvent event) {
        event.setCancelled(true);
    }

    // エンドポータル生成
    /*@EventHandler(ignoreCancelled = true)
    public void onPortalFrameFill(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block b = e.getClickedBlock();
        if (b == null) return;

        if (b.getType() == Material.END_PORTAL_FRAME) {
            ItemStack hand = e.getItem();
            if (hand != null && hand.getType() == Material.ENDER_EYE) {
                e.setCancelled(true);
            }
        }
    }*/

    // エンドラ討伐後のポータル生成制限
    @EventHandler(ignoreCancelled = true)
    public void onDragonDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof EnderDragon)) return;

        World world = e.getEntity().getWorld();
        if (world.getEnvironment() != World.Environment.THE_END) return;

        // 生成タイミングがズレるので複数回掃除
        cleanupLater(world, 160L);
        cleanupLater(world, 180L);
    }

    private void cleanupLater(World world, long delayTicks) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> removeReturnPortal(world), delayTicks);
    }

    private void removeReturnPortal(World world) {
        // 帰還ポータルはだいたい 0,0 付近。Yは環境でズレるので少し広めに走査
        int cx = 0;
        int cz = 0;
        int radius = 12;
        int minY = 40;
        int maxY = 120;


        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                for (int y = minY; y <= maxY; y++) {
                    Block b = world.getBlockAt(x, y, z);

                    // 帰還ポータル本体を消す
                    if (b.getType() == Material.END_PORTAL) {
                        b.setType(Material.AIR, false);
                    }

                    if (b.getType() == Material.BEDROCK) b.setType(Material.AIR, false);
                }
            }
        }
    }
}
