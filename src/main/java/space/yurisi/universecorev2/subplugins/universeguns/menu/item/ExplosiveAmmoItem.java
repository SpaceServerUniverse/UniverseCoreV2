package space.yurisi.universecorev2.subplugins.universeguns.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public class ExplosiveAmmoItem extends AmmoItem {

    public ExplosiveAmmoItem(UniverseCoreAPIConnector connector, int amount, Player player) {
        super(connector, amount, player);
    }

    @Override
    protected Material getMaterial() {
        return Material.TNT;
    }

    @Override
    protected String getDisplayName() {
        return "特殊系";
    }

    @Override
    protected long getCurrentAmmo() throws UserNotFoundException, AmmoNotFoundException {
        return connector.getAmmoFromUserId(player, GunType.EX);
    }

    @Override
    protected GunType getGunType() {
        return GunType.EX;
    }

    @Override
    protected int getAmountIron() {
        return 4;
    }

    @Override
    protected int getAmountPowder() {
        return 5;
    }

    @Override
    protected long getPrice() {
        return 300;
    }
}
