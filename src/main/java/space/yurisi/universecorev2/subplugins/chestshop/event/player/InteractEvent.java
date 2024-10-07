package space.yurisi.universecorev2.subplugins.chestshop.event.player;

import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.ChestShop;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.ChestShopRepository;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.ChestShopNotFoundException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.chestshop.utils.DoubleChestFinder;
import space.yurisi.universecorev2.subplugins.chestshop.utils.InventoryUtils;
import space.yurisi.universecorev2.subplugins.chestshop.utils.ItemUtils;
import space.yurisi.universecorev2.subplugins.chestshop.utils.SuperMessageHelper;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;

import java.util.UUID;

public class InteractEvent implements Listener {
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) throws ParameterException {

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
                    if (player.getUniqueId().toString().equals(chestShop.getUuid())) {
                        SuperMessageHelper.sendErrorMessage(player, "このチェストショップはあなたがオーナーなため買うことができません");
                        event.setCancelled(true);
                        return;
                    }
                    Integer emptySlot = player.getInventory().firstEmpty();

                    if (emptySlot == -1) {
                        SuperMessageHelper.sendErrorMessage(player, "インベントリーがいっぱいです");
                        event.setCancelled(true);
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
                        event.setCancelled(true);
                        return;
                    }
                    boolean doItemRemove;
                    int remaining = itemStack.getAmount();
                    doItemRemove = InventoryUtils.RemoveItemFormChest(chest, itemStack, remaining);
                    Chest chestBlockData = (Chest) chest.getBlockData();
                    if (!doItemRemove) {
                        if (chestBlockData.getType() != Chest.Type.SINGLE) {
                            BlockFace face = DoubleChestFinder.getNeighboringChestBlockFace(chestBlockData);
                            if (face != null) {
                                Block neighborBlock = chestBlock.getRelative(face);
                                if (neighborBlock.getState() instanceof org.bukkit.block.Chest) {
                                    doItemRemove = InventoryUtils.RemoveItemFormChest((org.bukkit.block.Chest) neighborBlock, itemStack, remaining);
                                }
                            }
                        }
                    }
                    if (!(doItemRemove)) {
                        SuperMessageHelper.sendErrorMessage(player, "チェスト内の在庫が不足しています");
                        event.setCancelled(true);
                        return;
                    }
                    try {
                        universeEconomyAPI.reduceMoney(player, chestShop.getPrice(), "チェストショップでの購入:" + ItemUtils.name(itemStack) + ":" + itemStack.getAmount());
                    } catch (UserNotFoundException | MoneyNotFoundException e) {
                        event.setCancelled(true);
                        return;
                    } catch (CanNotReduceMoneyException e) {
                        SuperMessageHelper.sendErrorMessage(player, "お金が不足しています");
                        event.setCancelled(true);
                        return;
                    }
                    try {
                        User owner = userRepository.getUserFromUUID(UUID.fromString(chestShop.getUuid()));
                        Money ownerMoney = moneyRepository.getMoneyFromUserId(owner.getId());
                        ownerMoney.setMoney(ownerMoney.getMoney() + chestShop.getPrice());
                        moneyRepository.updateMoney(ownerMoney, chestShop.getPrice(), "チェストショップでの売却:" + ItemUtils.name(itemStack) + ":" + itemStack.getAmount());
                    } catch (UserNotFoundException | MoneyNotFoundException e) {
                        event.setCancelled(true);
                        return;
                    }
                    player.getInventory().addItem(itemStack);
                    SuperMessageHelper.sendSuccessMessage(player, "チェストショップから" + ItemUtils.name(itemStack) + "を" + itemStack.getAmount() + "こ購入しました");
                    event.setCancelled(true);
                }
            }
        }
    }
}
