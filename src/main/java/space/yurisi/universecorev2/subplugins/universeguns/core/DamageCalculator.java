package space.yurisi.universecorev2.subplugins.universeguns.core;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class DamageCalculator {

    public static boolean isHeadShot(double height, Entity entity) {
        double neckHeight = 1.35;
        return height > entity.getLocation().getY() + neckHeight;
    }

    public static double getSlopedDamage(Location location, Entity entity, long range, double damage) {
        double distance = location.distance(entity.getLocation());
        double newDamage = damage * (1 - distance / range);
        if(distance > range){
            return 0.0D;
        }
        return newDamage;
    }
}
