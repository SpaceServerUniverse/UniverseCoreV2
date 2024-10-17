package space.yurisi.universecorev2.subplugins.universeguns.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;
import space.yurisi.universecorev2.utils.Message;

public class SubMachineGunAmmoItem extends AmmoItem {

    public SubMachineGunAmmoItem(UniverseCoreAPIConnector connector, int amount, Player player) {
        super(connector, amount, player);
    }

    @Override
    protected Material getMaterial() {
        return Material.LIME_CARPET;
    }

    @Override
    protected String getDisplayName() {
        return "サブマシンガン";
    }

    @Override
    protected long getCurrentAmmo() throws UserNotFoundException, AmmoNotFoundException {
        return connector.getAmmoFromUserId(player, GunType.SMG);
    }

    @Override
    protected GunType getGunType() {
        return GunType.SMG;
    }

    @Override
    protected int getAmountIron() {
        return 1;
    }

    @Override
    protected int getAmountPowder() {
        return 2;
    }

    @Override
    protected long getPrice() {
        return 300;
    }
}
