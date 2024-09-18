package space.yurisi.universecorev2.subplugins.sitdown.event;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.block.BlockBreakEvent;
import space.yurisi.universecorev2.subplugins.sitdown.Sitdown;

public final class EventListener implements Listener {


    private final Sitdown main;

    public EventListener(Sitdown main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) return;

        BlockData data = block.getBlockData();
        if (data instanceof Stairs) {
            Stairs stairs = (Stairs) block.getBlockData();
            if (stairs.getHalf() == Bisected.Half.TOP) {
                return;
            }
        }

        if (event.hasBlock() && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.hasItem()) {
            return;
        }

        Player player = event.getPlayer();

        if (player.isSneaking()) {
            return;
        }

        if (!block.getType().toString().contains("STAIRS")) {
            return;
        }
        if (block.getRelative(BlockFace.UP).getType().isSolid()) {
            return;
        }

        if (!player.isSneaking() && player.getVehicle() != null) {
            return;
        }

        if (main.existsVehicle(player)) {
            return;
        }

        sitDown(block, player);
    }

    @EventHandler
    public void onVehicle(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation().clone();
        location.add(0, 0.7, 0);
        if (!main.existsVehicle(player)) {
            return;
        }
        exitVehicle(player);
        player.teleport(location);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        exitVehicle(event.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        exitVehicle(event.getPlayer());
    }

    private void sitDown(Block block, Player player) {
        Location location = block.getLocation().clone();
        location.add(0.5, 0.4, 0.5);
        Stairs stairs = (Stairs) block.getBlockData();
        BlockFace blockface = stairs.getFacing();
        float yaw = switch (blockface) {
            case NORTH -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
        Stairs.Shape shape = stairs.getShape();
        switch (shape) {
            case INNER_LEFT:
                yaw -= 45;
                break;
            case INNER_RIGHT:
                yaw += 45;
                break;
            case OUTER_LEFT:
                yaw -= 45;
                break;
            case OUTER_RIGHT:
                yaw += 45;
                break;
            case STRAIGHT:
                yaw += 0;
                break;

        }

        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setInvisible(true);
        armorStand.setGravity(false);
        armorStand.setSilent(true);
        armorStand.setInvulnerable(true);
        armorStand.setMarker(true);
        armorStand.setRotation(yaw, 0);

        player.setRotation(yaw, 0);
        armorStand.addPassenger(player);

        main.setVehicle(player, armorStand);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        BlockData data = event.getBlock().getBlockData();
        if (data instanceof Stairs) {
            Location StairsLocation = event.getBlock().getLocation();
            for (Player player : StairsLocation.getWorld().getPlayers()) {
                if (player.getLocation().distance(StairsLocation) < 1.0) {
                    exitVehicle(player);
                }
            }
        }
    }

    private void exitVehicle(Player player) {
        if (!main.existsVehicle(player)) {
            return;
        }
        main.getVehicle(player).remove();
        main.removeVehicle(player);
    }
}