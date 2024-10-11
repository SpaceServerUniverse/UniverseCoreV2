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
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public abstract class AmmoItem extends AbstractItem {


    protected final UniverseCoreAPIConnector connector;
    protected final int amount;
    protected final Player player;

    public AmmoItem(UniverseCoreAPIConnector connector, int amount, Player player) {
        this.connector = connector;
        this.amount = amount;
        this.player = player;
    }

    @Override
    public ItemProvider getItemProvider(){
        try {
            return new ItemBuilder(getMaterial()).setAmount(amount).setDisplayName(getDisplayName()).addLoreLines(
                    "§7クリックで弾薬を作成します。",
                    "現在の弾薬数: " + getCurrentAmmo() + "発"
            );
        } catch (UserNotFoundException e) {
            Message.sendErrorMessage(player, "[武器AI]", "ユーザーが見つかりませんでした。");
        } catch (AmmoNotFoundException e) {
            Message.sendErrorMessage(player, "[武器AI]", "弾薬が見つかりませんでした。");
        }
        return null;
    }

    protected abstract Material getMaterial();

    protected abstract String getDisplayName();

    protected abstract long getCurrentAmmo() throws UserNotFoundException, AmmoNotFoundException;

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        try {
            Long nowAmmo = connector.getAmmoFromUserId(player, getGunType());
            connector.setAmmoFromUserId(player, getGunType(), nowAmmo + amount);
        } catch (UserNotFoundException e) {
            Message.sendErrorMessage(player, "[武器AI]", "ユーザーが見つかりませんでした。");
        } catch (AmmoNotFoundException e) {
            Message.sendErrorMessage(player, "[武器AI]", "弾薬が見つかりませんでした。");
        }
    }

    protected abstract GunType getGunType();
}
