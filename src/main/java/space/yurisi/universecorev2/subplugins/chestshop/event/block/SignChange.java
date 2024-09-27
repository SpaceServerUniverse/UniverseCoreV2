package space.yurisi.universecorev2.subplugins.chestshop.event.block;

import com.google.gson.JsonSyntaxException;
import io.papermc.paper.event.player.PlayerOpenSignEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.translation.Translator;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
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
import space.yurisi.universecorev2.subplugins.chestshop.utils.ItemUtils;
import space.yurisi.universecorev2.subplugins.chestshop.utils.NumberUtils;
import space.yurisi.universecorev2.subplugins.chestshop.utils.SuperMessageHelper;
import space.yurisi.universecorev2.subplugins.containerprotect.event.api.ContainerProtectAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.utils.Message;

import java.util.Objects;
import java.util.UUID;

public class SignChange implements Listener {
    @EventHandler
    public void onSignOpen(PlayerOpenSignEvent event) throws ParameterException {
        Player player = event.getPlayer();
        Sign sign = event.getSign();
        if (!(sign.getBlockData() instanceof WallSign)) return;
        ChestShopRepository chestShopRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getChestShopRepository();
        UniverseEconomyAPI universeEconomyAPI = UniverseEconomyAPI.getInstance();
        UserRepository userRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository();
        MoneyRepository moneyRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getMoneyRepository();
        Location location = sign.getLocation();
        ChestShop chestShop = null;
        try {
            chestShop = chestShopRepository.getChestShopBySignLocation(location);
        } catch (ChestShopNotFoundException e) {
            //NOOP
        }
        if (chestShop != null) {
            if (player.getUniqueId().toString().equals(chestShop.getUuid())) {
                player.sendMessage(SuperMessageHelper.getErrorMessage("このチェストショップはあなたがオーナーなため買うことができません"));
                event.setCancelled(true);
                return;
            }
            Integer emptySlot = player.getInventory().firstEmpty();

            if (emptySlot == -1) {
                player.sendMessage(SuperMessageHelper.getErrorMessage("インベントリーがいっぱいです"));
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
                player.sendMessage(SuperMessageHelper.getErrorMessage("不明なエラーが発生しました"));
                event.setCancelled(true);
                return;
            }

            Inventory chestInventory = chest.getBlockInventory();
            boolean doItemRemove = false;
            int remaining = itemStack.getAmount();
            for (ItemStack chestItemSlot : chestInventory.getContents()) {
                if (chestItemSlot != null && chestItemSlot.getType() == itemStack.getType()) {
                    if (chestItemSlot.getAmount() >= remaining) {
                        chestItemSlot.setAmount(chestItemSlot.getAmount() - remaining);
                        doItemRemove = true;
                        break;
                    }
                }
            }

            Chest chestBlockData = (Chest) chest.getBlockData();
            if (!doItemRemove) {
                if (chestBlockData.getType() != Chest.Type.SINGLE) {
                    BlockFace face = DoubleChestFinder.getNeighboringChestBlockFace(chestBlockData);
                    if (face != null) {
                        Block neighborBlock = chestBlock.getRelative(face);
                        if (neighborBlock.getState() instanceof org.bukkit.block.Chest) {
                            Inventory chestInventory2 = ((org.bukkit.block.Chest) neighborBlock.getState()).getBlockInventory();
                            for (ItemStack chestItemSlot : chestInventory2.getContents()) {
                                if (chestItemSlot != null && chestItemSlot.getType() == itemStack.getType()) {
                                    if (chestItemSlot.getAmount() >= remaining) {
                                        chestItemSlot.setAmount(chestItemSlot.getAmount() - remaining);
                                        doItemRemove = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!(doItemRemove)) {
                player.sendMessage(SuperMessageHelper.getErrorMessage("チェスト内の在庫が不足しています"));
                event.setCancelled(true);
                return;
            }

            try {
                universeEconomyAPI.reduceMoney(player, chestShop.getPrice(), "チェストショップでの購入:" + ItemUtils.name(itemStack) + ":" + itemStack.getAmount());
            } catch (UserNotFoundException | MoneyNotFoundException e) {
                event.setCancelled(true);
                return;
            } catch (CanNotReduceMoneyException e) {
                player.sendMessage(SuperMessageHelper.getSuccessMessage("お金が不足しています"));
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
            player.sendMessage(SuperMessageHelper.getSuccessMessage("チェストショップから" + ItemUtils.name(itemStack) + "を" + itemStack.getAmount() + "こ購入しました"));
            event.setCancelled(true);
        } else {
            BlockData signBlockData = sign.getBlockData();
            if (!(signBlockData instanceof WallSign)) return;

            String[] lines = sign.getLines();

            String topText = lines[0];
            String PriceText = lines[1];
            String AmountText = lines[2];
            String ItemText = lines[3];

            if (!topText.isEmpty() || PriceText.isEmpty() || AmountText.isEmpty() || ItemText.isEmpty()) {
                return;
            }
            if (!NumberUtils.isNumeric(PriceText) || !NumberUtils.isNumeric(AmountText)) {
                return;
            }
            int price = Integer.parseInt(PriceText);
            int amount = Integer.parseInt(AmountText);
            ItemStack itemStack2;
            if(ItemText.equals("?")){
                itemStack2 = new ItemStack(player.getInventory().getItemInMainHand());
                player.sendMessage("手持ちにあるアイテムを売るアイテムに設定しました");
            }else {
                try {
                    itemStack2 = ItemStack.of(Objects.requireNonNull(Material.getMaterial(ItemText.toUpperCase())), amount);
                } catch (IllegalArgumentException | NullPointerException e) {
                    Message.sendErrorMessage(player, "ChestShop", "アイテム名から調べたけどアイテムが見つからないよ");
                    event.setCancelled(true);
                    return;
                }
            }
            BlockFace signFace = ((WallSign) signBlockData).getFacing().getOppositeFace();
            Block mainChest = sign.getBlock().getRelative(signFace);

            BlockData mainChestData = mainChest.getBlockData();
            if (!(mainChestData instanceof Chest)) return;
            SignSide signLine = sign.getSide(Side.FRONT);
            signLine.line(0, Component.text(player.getName()));
            signLine.line(1, Component.text("値段:" + price));
            signLine.line(2, Component.text("個数:" + amount));
            signLine.line(3, Component.text(ItemUtils.name(itemStack2)));

            itemStack2.setItemMeta(null);

            UniverseCoreV2API.getInstance().getDatabaseManager().getChestShopRepository().createChestShop(player,itemStack2 , (long) price, sign.getBlock(), mainChest);
            ContainerProtectAPI.getInstance().addContainerProtect(player, mainChest.getLocation());
            player.sendMessage(SuperMessageHelper.getSuccessMessage("チェストショップを作成しました"));
            sign.update(true, false);
            event.setCancelled(true);
        }
    }
}
