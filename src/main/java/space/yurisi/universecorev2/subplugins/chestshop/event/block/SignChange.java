package space.yurisi.universecorev2.subplugins.chestshop.event.block;

import com.google.gson.JsonSyntaxException;
import io.papermc.paper.event.player.PlayerOpenSignEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
import space.yurisi.universecorev2.subplugins.chestshop.utils.*;
import space.yurisi.universecorev2.subplugins.containerprotect.event.api.ContainerProtectAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.utils.NumberUtils;

import java.util.Objects;
import java.util.UUID;

public class SignChange implements Listener {
    @EventHandler
    public void onSignOpen(PlayerOpenSignEvent event) throws ParameterException {
        Player player = event.getPlayer();
        Sign sign = event.getSign();
        if (!(sign.getBlockData() instanceof WallSign)) return;

        ChestShopRepository chestShopRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getChestShopRepository();
        Location location = sign.getLocation();
        ChestShop chestShop = null;

        try {
            chestShop = chestShopRepository.getChestShopBySignLocation(location);
        } catch (ChestShopNotFoundException ignored) {
            //NOOP
        }
        if (chestShop == null) {
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
            if (price < 1) {
                SuperMessageHelper.sendErrorMessage(player, "値段は1以上で指定してください");
                event.setCancelled(true);
                return;
            }
            int amount = Integer.parseInt(AmountText);
            if (amount < 1 || amount > 99) {
                SuperMessageHelper.sendErrorMessage(player, "個数は1〜99の範囲で指定してください");
                event.setCancelled(true);
                return;
            }
            ItemStack itemStack2;
            if (ItemText.equals("?")) {
                itemStack2 = ItemStack.of(player.getInventory().getItemInMainHand().getType(), amount);
                SuperMessageHelper.sendSuccessMessage(player, "手持ちにあるアイテムを売るアイテムに設定しました");
            } else {
                try {
                    itemStack2 = ItemStack.of(Objects.requireNonNull(Material.getMaterial(ItemText.toUpperCase())), amount);
                } catch (IllegalArgumentException | NullPointerException e) {
                    SuperMessageHelper.sendErrorMessage(player, "アイテム名から調べたけどアイテムが見つからないよ\n/itemを使うか\n看板の4行目を?にして売りたいアイテムをメインハンドにもってクリックしてね");
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

            UniverseCoreV2API.getInstance().getDatabaseManager().getChestShopRepository().createChestShop(player, itemStack2, (long) price, sign.getBlock(), mainChest);
            ContainerProtectAPI.getInstance().addContainerProtect(player, mainChest.getLocation());
            SuperMessageHelper.sendSuccessMessage(player, "チェストショップを作成しました");
            sign.update(true, false);
            event.setCancelled(true);
        }

    }
}
