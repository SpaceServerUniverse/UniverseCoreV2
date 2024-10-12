package space.yurisi.universecorev2.subplugins.universeguns.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public class AssaultRifleAmmoItem extends AmmoItem {

    public AssaultRifleAmmoItem(UniverseCoreAPIConnector connector, int amount, Player player) {
        super(connector, amount, player);
    }

    @Override
    protected Material getMaterial() {
        return Material.LIME_CARPET;
    }

    @Override
    protected String getDisplayName() {
        return "アサルトライフル";
    }

    @Override
    protected long getCurrentAmmo() throws UserNotFoundException, AmmoNotFoundException {
        return connector.getAmmoFromUserId(player, GunType.AR);
    }

    @Override
    protected GunType getGunType() {
        return GunType.AR;
    }

    @Override
    protected int getAmountIron() {
        return 2;
    }

    @Override
    protected int getAmountPowder() {
        return 2;
    }

    @Override
    protected long getPrice() {
        return 200;
    }

}
