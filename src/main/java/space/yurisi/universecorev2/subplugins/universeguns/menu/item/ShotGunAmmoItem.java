package space.yurisi.universecorev2.subplugins.universeguns.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public class ShotGunAmmoItem extends AmmoItem {

    public ShotGunAmmoItem(UniverseCoreAPIConnector connector, int amount, Player player) {
        super(connector, amount, player);
    }

    @Override
    protected Material getMaterial() {
        return Material.ORANGE_CARPET;
    }

    @Override
    protected String getDisplayName() {
        return "ショットガン";
    }

    @Override
    protected long getCurrentAmmo() throws UserNotFoundException, AmmoNotFoundException {
        return connector.getAmmoFromUserId(player, GunType.SG);
    }

    @Override
    protected GunType getGunType() {
        return GunType.SG;
    }
}
