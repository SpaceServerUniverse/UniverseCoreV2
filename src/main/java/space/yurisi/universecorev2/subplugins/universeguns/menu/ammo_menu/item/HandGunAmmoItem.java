package space.yurisi.universecorev2.subplugins.universeguns.menu.ammo_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public class HandGunAmmoItem extends AmmoItem {

    public HandGunAmmoItem(UniverseCoreAPIConnector connector, int amount, Player player) {
        super(connector, amount, player);
    }

    @Override
    protected Material getMaterial() {
        return Material.RED_CARPET;
    }

    @Override
    protected String getDisplayName() {
        return "ハンドガン";
    }

    @Override
    protected long getCurrentAmmo() throws UserNotFoundException, AmmoNotFoundException {
        return connector.getAmmoFromUserId(player, GunType.HG);
    }

    @Override
    protected GunType getGunType() {
        return GunType.HG;
    }

    @Override
    protected int getAmountIron() {
        return 1;
    }

    @Override
    protected int getAmountPowder() {
        return 1;
    }

    @Override
    protected long getPrice() {
        return 300;
    }
}
