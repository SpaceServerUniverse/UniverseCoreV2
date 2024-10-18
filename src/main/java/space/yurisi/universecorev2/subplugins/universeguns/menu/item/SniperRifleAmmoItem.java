package space.yurisi.universecorev2.subplugins.universeguns.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public class SniperRifleAmmoItem extends AmmoItem {

    public SniperRifleAmmoItem(UniverseCoreAPIConnector connector, int amount, Player player) {
        super(connector, amount, player);
    }

    @Override
    protected Material getMaterial() {
        return Material.BLUE_CARPET;
    }

    @Override
    protected String getDisplayName() {
        return "スナイパーライフル";
    }

    @Override
    protected long getCurrentAmmo() throws UserNotFoundException, AmmoNotFoundException {
        return connector.getAmmoFromUserId(player, GunType.SR);
    }

    @Override
    protected GunType getGunType() {
        return GunType.SR;
    }

    @Override
    protected int getAmountIron() {
        return 3;
    }

    @Override
    protected int getAmountPowder() {
        return 3;
    }

    @Override
    protected long getPrice() {
        return 300;
    }
}
