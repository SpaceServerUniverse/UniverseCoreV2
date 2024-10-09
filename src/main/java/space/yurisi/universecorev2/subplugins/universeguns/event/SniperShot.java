package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.universeguns.core.GunStatus;

public class SniperShot {

    public SniperShot(Player player, Gun gun, GunStatus gunStatus) {

        gunStatus.updateActionBar(player, true);

        Vector direction = player.getEyeLocation().getDirection().normalize();

        if(!player.isSneaking()){
            Knockback(player, direction);
        }

        ShotEffect(player, gun, direction, player.getEyeLocation());
        RayTraceResult entityResult = detectEntities(player);
        RayTraceResult blockResult = player.getWorld().rayTraceBlocks(player.getEyeLocation(), direction, 500);
        setDamage(player, entityResult, blockResult, gun);
    }

    private void Knockback(Player player, Vector direction) {
        // 水平方向ベクトル
        Vector horizontalKnockback = direction.clone().setY(0).normalize().multiply(-1);
        player.setVelocity(player.getVelocity().add(horizontalKnockback));
    }

    public RayTraceResult detectEntities(Player player) {
        World world = player.getWorld();
        Vector direction = player.getEyeLocation().getDirection();


        return world.rayTraceEntities(player.getEyeLocation(), direction, 500, e -> e != player);
    }

    private void ShotEffect(Player player, Gun gun, Vector direction, Location startLocation) {
        player.getWorld().playSound(player.getLocation(), gun.getShotSound(), gun.getVolumeSound(), gun.getPitchSound());

        World world = player.getWorld();
        for (int i = 0; i < 500; i += 5) {
            Location particleLocation = startLocation.clone().add(direction.clone().multiply(i));
            RayTraceResult result = world.rayTraceBlocks(startLocation, direction, i);
            if (result != null && (result.getHitBlock() != null || result.getHitEntity() != null)) {
                break;
            }
            world.spawnParticle(Particle.WHITE_SMOKE, particleLocation, 0);
        }
    }

    private void setDamage(Player player, RayTraceResult entityResult, RayTraceResult blockResult, Gun gun) {
        if(entityResult == null){
            return;
        }
        Entity entity = entityResult.getHitEntity();
        if(blockResult == null){
            hit(entity, entityResult, gun, player);
            return;
        }
        Block block = blockResult.getHitBlock();
        if (entity == null) {
            return;
        }
        if(block == null){
            return;
        }
        if(block.getLocation().distance(player.getEyeLocation()) < entity.getLocation().distance(player.getLocation())){
            return;
        }

        hit(entity, entityResult, gun, player);
    }

    private void hit(Entity entity, RayTraceResult result, Gun gun, Player player) {
        if (entity instanceof LivingEntity livingEntity) {
            double height = result.getHitPosition().getY();
            double damage = gun.getBaseDamage();
            double neckHeight = 1.5D;
            if (height > livingEntity.getLocation().getY() + neckHeight) {
                damage *= 1.5D;
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
            } else {
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1.0F, 1.0F);
            }
            livingEntity.damage(damage, player);
        }
    }

}
