package space.yurisi.universecorev2.subplugins.universeguns.constants;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.item.gun.Gun;

public class BulletData {

    public Gun gun;
    public Player player;


    public BulletData(Gun gun, Player player) {
        this.gun = gun;
        this.player = player;
    }

    public Gun getGun() {
        return gun;
    }

    public Player getPlayer() {
        return player;
    }
}
