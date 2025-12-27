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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.ChestShop;
import space.yurisi.universecorev2.database.repositories.ChestShopRepository;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.ChestShopNotFoundException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.InconsistentTransactionException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.Transaction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.InterruptTransactionException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.AddItemAction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.DepositMoneyAction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.WithdrawMoneyAction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.RemoveItemAction;
import space.yurisi.universecorev2.subplugins.chestshop.utils.ItemUtils;
import space.yurisi.universecorev2.subplugins.chestshop.utils.SuperMessageHelper;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;

import java.util.ArrayList;
import java.util.UUID;

public class InteractEvent implements Listener {
    private static final Logger logger = LoggerFactory.getLogger(InteractEvent.class);

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

                    ArrayList<String> notes = new ArrayList<>();
                    Transaction tx = Transaction.create(logger)
                            .then(new WithdrawMoneyAction(universeEconomyAPI, player, chestShop.getPrice(), "チェストショップでの購入:" + ItemUtils.name(itemStack) + ":" + itemStack.getAmount())
                                    .whenMissingAccount(ctx -> notes.add("購入者の口座が見つかりませんでした"))
                                    .whenInsufficientBalance(ctx -> notes.add("お金が不足しています"))
                                    .whenRollbackMissingAccount(ctx -> notes.add("購入者の口座が見つかりませんでした"))
                                    .whenRollbackExceededBalance(ctx -> notes.add("組戻し失敗: 購入者の口座上限です"))
                            )
                            .then(new AddItemAction(player.getInventory(), itemStack)
                                    .whenNoSpaceLeft(ctx -> notes.add("インベントリーがいっぱいです"))
                            )
                            .then(new RemoveItemAction(chest.getInventory(), itemStack)
                                    .whenInsufficientItem(ctx -> notes.add("チェスト内の在庫が不足しています"))
                            )
                            .then(new DepositMoneyAction(userRepository, moneyRepository, UUID.fromString(chestShop.getUuid()), chestShop.getPrice(), "チェストショップでの売却:" + ItemUtils.name(itemStack) + ":" + itemStack.getAmount())
                                    .whenMissingAccount(ctx -> notes.add("販売者の口座が見つかりませんでした"))
                                    .whenRollbackMissingAccount(ctx -> notes.add("組戻し失敗: 販売者の講座が見つまりません"))
                            );
                    try {
                        tx.commit();
                    } catch (InterruptTransactionException e) {
                        for (String note: notes) {
                            SuperMessageHelper.sendErrorMessage(player, note);
                        }
                        return;
                    } catch (InconsistentTransactionException e) {
                        Bukkit.shutdown();
                    }

                    SuperMessageHelper.sendSuccessMessage(player, "チェストショップから" + ItemUtils.name(itemStack) + "を" + itemStack.getAmount() + "こ購入しました");
                    event.setCancelled(true);
                }
            }
        }
    }
}
