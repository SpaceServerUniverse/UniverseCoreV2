package space.yurisi.universecorev2.subplugins.universeguns.event;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.universeguns.UniverseGuns;
import space.yurisi.universecorev2.subplugins.universeguns.core.GunStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShotgunShot {

    List<Snowball> projectiles = new ArrayList<>();

    public ShotgunShot(Player player, Gun gun, GunStatus gunStatus, ArrayList<Player> isZoom) {

        gunStatus.updateActionBar(player, isZoom.contains(player));

        final Vector direction = player.getEyeLocation().getDirection().normalize();
        double spread = gun.getSpread() / 8.0;
        int bulletNumber = gun.getBulletNumber();



        if(!player.isSneaking()){
            Knockback(player, direction);
            spread *= 2.0;
        }

        if(!isZoom.contains(player)){
            spread *= 2.0;
        }



        for (int i = 0; i < bulletNumber; i++) {
            Vector spreadDirection = SpreadProjectile(direction.clone(), spread);
            Vector velocity = spreadDirection.multiply(gun.getBulletSpeed());
            Snowball projectile = player.launchProjectile(Snowball.class, velocity);
//            projectile.setGravity(false);
            ItemStack itemStack = new ItemStack(Material.SNOWBALL);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.setItemModel(new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.GUN_ITEM_MODEL));
                itemStack.setItemMeta(itemMeta);
                itemStack.setData(DataComponentTypes.CUSTOM_MODEL_DATA, UniverseGuns.getInstance().getBulletData());
            }
            projectile.setItem(itemStack);
            projectiles.add(projectile);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!projectile.isDead()) {
                        projectile.remove();
                    }
                }
            }.runTaskLater(UniverseCoreV2.getInstance(), 40);
        }
        player.getWorld().playSound(player.getLocation(), gun.getShotSound(), gun.getVolumeSound(), gun.getPitchSound());

    }

    private Vector SpreadProjectile(Vector direction, double spread) {
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

    public List<Snowball> getProjectiles() {
        return projectiles;
    }
}
