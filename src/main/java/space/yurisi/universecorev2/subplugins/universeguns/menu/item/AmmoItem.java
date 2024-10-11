package space.yurisi.universecorev2.subplugins.universeguns.menu.item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;
import space.yurisi.universecorev2.subplugins.universeguns.menu.AmmoManagerInventoryMenu;
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
            return new ItemBuilder(getMaterial()).setDisplayName(getDisplayName()).addLoreLines(
                    "§7クリックで" + amount + "発の弾薬を作成します。",
                    "§7左クリックで鉄と火薬からクラフトします。",
                    "§7右クリックでお金で購入します。",
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
            boolean isCrafted = false;
            if(clickType == ClickType.LEFT){
                isCrafted = CraftItem(getAmountIron(), getAmountPowder());
            } else if(clickType == ClickType.RIGHT){
                isCrafted = BuyItem(getPrice());
            }

            if(!isCrafted){
                return;
            }
            connector.setAmmoFromUserId(player, getGunType(), nowAmmo + (long)amount);
        } catch (UserNotFoundException e) {
            Message.sendErrorMessage(player, "[武器AI]", "ユーザーが見つかりませんでした。");
        } catch (AmmoNotFoundException e) {
            Message.sendErrorMessage(player, "[武器AI]", "弾薬が見つかりませんでした。");
        }
        AmmoManagerInventoryMenu ammoManagerInventoryMenu = new AmmoManagerInventoryMenu(connector);
        ammoManagerInventoryMenu.sendMenu(player);
    }

    protected abstract GunType getGunType();

    protected abstract int getAmountIron();

    protected abstract int getAmountPowder();

    protected abstract long getPrice();

    public boolean CraftItem(int amountIron, int amountPowder){
        if(hasItemForCrafting(amountIron, amountPowder)){
            player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, amountIron));
            player.getInventory().removeItem(new ItemStack(Material.GUNPOWDER, amountPowder));
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
            return true;
        }
        Message.sendErrorMessage(player, "[武器AI]", "アイテムが足りません。");
        return false;
    }

    public boolean hasItemForCrafting(int amountIron, int amountPowder){
        return player.getInventory().contains(Material.IRON_INGOT, amountIron) && player.getInventory().contains(Material.GUNPOWDER, amountPowder);
    }

    public boolean BuyItem(long price){
        UniverseEconomyAPI universeEconomyAPI = UniverseEconomyAPI.getInstance();
        try {
            Long money = universeEconomyAPI.getMoney(player);
            if(money >= price) {
                universeEconomyAPI.reduceMoney(player, price, "弾薬の購入");
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                return true;
            }
            Message.sendErrorMessage(player, "[武器AI]", "お金が足りません。");
            return false;
        } catch (UserNotFoundException e) {
            Message.sendErrorMessage(player, "[武器AI]", "ユーザーが見つかりませんでした。");
        } catch (MoneyNotFoundException e) {
            Message.sendErrorMessage(player, "[武器AI]", "お金が見つかりませんでした。");
        } catch (CanNotReduceMoneyException e) {
            Message.sendErrorMessage(player, "[武器AI]", "お金を減らすことができませんでした。");
        } catch (ParameterException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
