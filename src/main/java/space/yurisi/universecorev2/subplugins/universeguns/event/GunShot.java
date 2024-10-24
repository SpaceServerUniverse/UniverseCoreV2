package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;
import space.yurisi.universecorev2.subplugins.universeguns.core.GunStatus;

import java.util.ArrayList;
import java.util.Random;

public class GunShot {

    Snowball projectile;

    Player player;

    public GunShot(Player player, Gun gun, GunStatus gunStatus, ArrayList<Player> isZoom) {

        this.player = player;

        gunStatus.updateActionBar(player, isZoom.contains(player));

        Vector direction = player.getEyeLocation().getDirection().normalize();

        if((gun.getType().equals(GunType.SG) || gun.getType().equals(GunType.EX)) && !player.isSneaking()){
            Knockback(player, direction);
        }
        // ADSしていない場合は精度を下げる
        if(!isZoom.contains(player)){
            direction = SpreadProjectile(direction, gun);
        }

        Vector velocity = direction.multiply(gun.getBulletSpeed());
        projectile = player.launchProjectile(Snowball.class, velocity);
        ItemStack itemStack = new ItemStack(Material.SNOWBALL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setCustomModelData(1);
            itemStack.setItemMeta(itemMeta);
        }
        projectile.setItem(itemStack);
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

    private Vector SpreadProjectile(Vector direction, Gun gun) {
        double spread = gun.getSpread();
        spread /= 5.0;
        Random random = new Random();
        double spreadX = random.nextGaussian() * spread;
        double spreadY = random.nextGaussian() * spread;
        double spreadZ = random.nextGaussian() * spread;
        return direction.add(new Vector(spreadX, spreadY, spreadZ)).normalize();
    }

    private void Knockback(Player player, Vector direction) {
        // 水平方向ベクトル
        Vector horizontalKnockback = direction.clone().setY(0).normalize().multiply(-1);
        player.setVelocity(player.getVelocity().add(horizontalKnockback));
    }

    public void ShotEffect(Player player, Gun gun, Location loc) {
        player.getWorld().playSound(player.getLocation(), gun.getShotSound(), gun.getVolumeSound(), gun.getPitchSound());
    }

    public Entity getProjectile() {
        return projectile;
    }

    public Location getLaunchLocation() {
        return player.getEyeLocation();
    }
}
