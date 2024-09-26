package space.yurisi.universecorev2.subplugins.universeguns.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;

public class ShotEvent {

    Snowball projectile;

    public ShotEvent(Player player, GunItem gun) {
        Location loc = player.getEyeLocation();
        Vector velocity = player.getLocation().getDirection().multiply(gun.getBulletSpeed());

        projectile = player.launchProjectile(Snowball.class);
        projectile.setVelocity(velocity);
    }

    public Entity getProjectile() {
        return projectile;
    }
}
