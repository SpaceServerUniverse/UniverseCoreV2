package space.yurisi.universecorev2.subplugins.universeguns.menu.ammo_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

public class LightMachineGunAmmoItem extends AmmoItem {
    public LightMachineGunAmmoItem(UniverseCoreAPIConnector connector, int amount, Player player) {
        super(connector, amount, player);
    }

    @Override
    protected Material getMaterial() {
        return Material.LIME_CARPET;
    }

    @Override
    protected String getDisplayName() {
        return "軽機関銃";
    }

    @Override
    protected long getCurrentAmmo() throws UserNotFoundException, AmmoNotFoundException {
        return connector.getAmmoFromUserId(player, GunType.LMG);
    }

    @Override
    protected GunType getGunType() {
        return GunType.LMG;
    }

    @Override
    protected int getAmountIron() {
        return 3;
    }

    @Override
    protected int getAmountPowder() {
        return 4;
    }

    @Override
    protected long getPrice() {
        return 300;
    }
}
