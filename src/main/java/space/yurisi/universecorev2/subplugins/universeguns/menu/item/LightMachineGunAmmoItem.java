package space.yurisi.universecorev2.subplugins.universeguns.menu.item;

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
        return "ライトマシンガン";
    }

    @Override
    protected long getCurrentAmmo() throws UserNotFoundException, AmmoNotFoundException {
        return connector.getAmmoFromUserId(player, GunType.LMG);
    }

    @Override
    protected GunType getGunType() {
        return GunType.LMG;
    }
}
