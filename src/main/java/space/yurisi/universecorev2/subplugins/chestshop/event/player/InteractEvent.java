package space.yurisi.universecorev2.subplugins.chestshop.event.player;

import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.ChestShop;
import space.yurisi.universecorev2.database.repositories.ChestShopRepository;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.ChestShopNotFoundException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.Transaction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.TransactionException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.AddItemAction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.AddMoneyAction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.ReduceMoneyAction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.RemoveItemAction;
import space.yurisi.universecorev2.subplugins.chestshop.utils.ItemUtils;
import space.yurisi.universecorev2.subplugins.chestshop.utils.SuperMessageHelper;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universejob.util.MarketPriceChanger;

import java.util.List;
import java.util.UUID;

public class InteractEvent implements Listener {
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getHand() == EquipmentSlot.HAND) {
                Player player = event.getPlayer();
                Block block = event.getClickedBlock();
                if (!(block.getBlockData() instanceof WallSign)) return;
                ChestShopRepository chestShopRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getChestShopRepository();
                UserRepository userRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository();
                UniverseEconomyAPI universeEconomyAPI = UniverseEconomyAPI.getInstance();
                MoneyRepository moneyRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getMoneyRepository();
                Location location = block.getLocation();
                ChestShop chestShop = null;

                try {
                    chestShop = chestShopRepository.getChestShopBySignLocation(location);
                } catch (ChestShopNotFoundException ignored) {
                    //NOOP
                }
                if (chestShop != null) {
                    event.setCancelled(true);
                    if (player.getUniqueId().toString().equals(chestShop.getUuid())) {
                        SuperMessageHelper.sendErrorMessage(player, "このチェストショップはあなたがオーナーなため買うことができません");
                        return;
                    }

                    World world = Bukkit.getWorld(chestShop.getWorld_name());
                    Location chestLocation = new Location(world, chestShop.getMainChest_x(), chestShop.getMainChest_y(), chestShop.getMainChest_z());
                    Block chestBlock = chestLocation.getBlock();
                    if (!(chestBlock.getType() == Material.CHEST)) return;
                    org.bukkit.block.Chest chest = (org.bukkit.block.Chest) chestBlock.getState();
                    ItemStack itemStack;
                    try {
                        itemStack = ItemUtils.deserialize(chestShop.getItem());
                    } catch (IllegalArgumentException | JsonSyntaxException e) {
                        SuperMessageHelper.sendErrorMessage(player, "不明なエラーが発生しました");
                        return;
                    }

                    Transaction tx = Transaction.create()
                            .then(new ReduceMoneyAction(universeEconomyAPI, player, MarketPriceChanger.purchaserPriceChanger(player, itemStack, chestShop.getPrice()), "チェストショップでの購入:" + ItemUtils.name(itemStack) + ":" + itemStack.getAmount()))
                            .then(new AddItemAction(player.getInventory(), itemStack))
                            .then(new RemoveItemAction(chest.getInventory(), itemStack))
                            .then(new AddMoneyAction(userRepository, moneyRepository, UUID.fromString(chestShop.getUuid()), MarketPriceChanger.sellerPriceChanger(chestShop.getUuid(), itemStack, chestShop.getPrice()), "チェストショップでの売却:" + ItemUtils.name(itemStack) + ":" + itemStack.getAmount()));

                    try {
                        tx.commit();
                    } catch (TransactionException e) {
                        SuperMessageHelper.sendErrorMessage(player, formatErrorMessage(e));
                        return;
                    }

                    SuperMessageHelper.sendSuccessMessage(player, "チェストショップから" + ItemUtils.name(itemStack) + "を" + itemStack.getAmount() + "こ購入しました");
                    event.setCancelled(true);
                }
            }
        }
    }

    private static @NonNull String formatErrorMessage(TransactionException e) {
        List<String> messages = new java.util.ArrayList<>();
        messages.add(e.getFriendlyMessage());
        for (Throwable suppressed: e.getSuppressed()) {
            if (suppressed instanceof TransactionException txException) {
                messages.add(txException.getFriendlyMessage());
            } else {
                messages.add(suppressed.getMessage());
            }
        }

        return String.join("\n + ", messages);
    }
}
