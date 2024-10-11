package space.yurisi.universecorev2.subplugins.universeguns.core;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.item.gun.Gun;

public class BulletData {

    public Gun gun;
    public Player player;
    public Location location;


    public BulletData(Gun gun, Player player, Location location) {
        this.gun = gun;
        this.player = player;
        this.location = location;
    }

    public Gun getGun() {
        return gun;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }
}
