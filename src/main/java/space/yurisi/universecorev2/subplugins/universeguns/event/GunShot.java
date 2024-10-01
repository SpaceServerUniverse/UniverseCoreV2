package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;

import java.util.ArrayList;
import java.util.Random;

public class GunShot {

    Snowball projectile;

    public GunShot(Player player, GunItem gun, ArrayList<Player> isZoom) {

        gun.updateActionBar(player, isZoom.contains(player));

        Vector direction = player.getEyeLocation().getDirection().normalize();

        if((gun.getType().equals("SG") || gun.getType().equals("EX")) && !player.isSneaking()){
            Knockback(player, direction);
        }
        // ADSしていない場合は精度を下げる
        if(!isZoom.contains(player)){
            direction = SpreadProjectile(direction, gun);
        }

        Vector velocity = direction.multiply(gun.getBulletSpeed());
        projectile = player.launchProjectile(Snowball.class, velocity);
        projectile.setGravity(false);
        ShotEffect(player, gun, projectile.getLocation());

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!projectile.isDead()) {
                    projectile.remove();
                }
            }
        }.runTaskLater(UniverseCoreV2.getInstance(), 40);

    }

    private Vector SpreadProjectile(Vector direction, GunItem gun) {
        double spread = gun.getSpread();
        Random random = new Random();
        double spreadX = (random.nextDouble() - 0.5) * spread;
        double spreadY = (random.nextDouble() - 0.5) * spread;
        double spreadZ = (random.nextDouble() - 0.5) * spread;
        return direction.add(new Vector(spreadX, spreadY, spreadZ)).normalize();
    }

    private void Knockback(Player player, Vector direction) {
        // 水平方向ベクトル
        Vector horizontalKnockback = direction.clone().setY(0).normalize().multiply(-1);
        player.setVelocity(player.getVelocity().add(horizontalKnockback));
    }

    public void ShotEffect(Player player, GunItem gun, Location loc) {
        player.getWorld().playSound(player.getLocation(), gun.getShotSound(), gun.getVolumeSound(), gun.getPitchSound());
    }

    public Entity getProjectile() {
        return projectile;
    }
}
