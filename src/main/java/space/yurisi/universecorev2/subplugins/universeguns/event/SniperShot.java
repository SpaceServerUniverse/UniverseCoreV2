package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;

public class SniperShot {

    public SniperShot(Player player, GunItem gun) {
        PlayerInventory inventory = player.getInventory();
        inventory.setItemInMainHand(gun.getItem());

        gun.updateActionBar(player, true);

        Vector direction = player.getEyeLocation().getDirection().normalize();

        if(!player.isSneaking()){
            Knockback(player, direction);
        }

        ShotEffect(player, gun, direction, player.getEyeLocation());
        RayTraceResult result = detectEntities(player);
        setDamage(player, result, gun);
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

    private void ShotEffect(Player player, GunItem gun, Vector direction, Location startLocation) {
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

    private void setDamage(Player player, RayTraceResult result, GunItem gun) {
        if(result == null){
            return;
        }
        Entity entity = result.getHitEntity();
        if (entity instanceof LivingEntity livingEntity) {
            double height = result.getHitPosition().getY();
            double damage = gun.getBaseDamage();
            double neckHeight = 1.5D;
            if (height > entity.getLocation().getY() + neckHeight) {
                damage *= 1.5D;
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
            }
            livingEntity.damage(damage, player);
        }
    }

}
