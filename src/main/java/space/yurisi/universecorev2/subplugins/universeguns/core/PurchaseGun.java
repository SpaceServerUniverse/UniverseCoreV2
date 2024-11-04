package space.yurisi.universecorev2.subplugins.universeguns.core;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.receivebox.ReceiveBoxAPI;
import space.yurisi.universecorev2.utils.Message;

import java.util.Date;

public class PurchaseGun {

    public static void executePurchase(Player player, Gun gun) {
        Boolean canPurchase = UniverseItem.removeItem(player, "gun_ticket", gun.getPrice());

        if(!canPurchase) {
            Message.sendWarningMessage(player, "[武器AI]", "チケットが足りません。");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        CustomItem item = UniverseItem.getItem(gun.getId());
        if(item == null) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            Message.sendErrorMessage(player, "[武器AI]", "アイテムが見つかりませんでした。運営にお問い合わせください。");
            return;
        }
        Inventory inventory = player.getInventory();
        ItemStack itemStack = item.getItem();

        if(inventory.firstEmpty() == -1) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
            Message.sendSuccessMessage(player, "[武器AI]", "インベントリがいっぱいなので、受け取りboxに送りました。");
            Message.sendSuccessMessage(player, "[武器AI]", "受け取りboxは /receive で確認できます。");
            Date expireDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);
            ReceiveBoxAPI.AddReceiveItem(itemStack, player.getUniqueId(), expireDate, "購入した武器");
            return;
        }

        inventory.addItem(itemStack);
        Message.sendSuccessMessage(player, "[武器AI]", gun.getName() + "を購入しました。");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 1, 1);
    }
}
