package space.yurisi.universecorev2.subplugins.freemarket.menu.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Market;
import space.yurisi.universecorev2.exception.MarketItemNotFoundException;
import space.yurisi.universecorev2.subplugins.freemarket.command.marketCommand;
import space.yurisi.universecorev2.subplugins.receivebox.ReceiveBoxAPI;
import space.yurisi.universecorev2.utils.Message;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class RemoveItem extends AbstractItem {

    private final Market item;

    public RemoveItem(Market item){
        this.item = item;
    }
    @Override
    public ItemProvider getItemProvider() {
        ItemStack itemStack = ItemStack.deserializeBytes(this.item.getSerializedItem());
        ItemBuilder itemBuilder = new ItemBuilder(itemStack)
                .setAmount(itemStack.getAmount());
        ItemMeta meta = itemStack.getItemMeta();
        if(meta instanceof Damageable damageable){
            itemBuilder.setDamage(damageable.getDamage());
        }

        return itemBuilder;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        try {
            // 7日後に期限切れ
            Instant expireInstant = Instant.now().plus(7, ChronoUnit.DAYS);
            Date expireDate = Date.from(expireInstant);

            ReceiveBoxAPI.AddReceiveItem(ItemStack.deserializeBytes(this.item.getSerializedItem()), player.getUniqueId(), expireDate, "フリーマーケットでの出品を取り消しました。");
            Market market = UniverseCoreV2API.getInstance().getDatabaseManager().getMarketRepository().removeItem(this.item.getId(), true);
            UniverseCoreV2API.getInstance().getDatabaseManager().getMarketRepository().addPurchased(market, player);
            Message.sendSuccessMessage(player, marketCommand.FreeMarketMessage, "出品を取り消しました");
        } catch (MarketItemNotFoundException e) {
            Message.sendErrorMessage(player, marketCommand.FreeMarketMessage, "アイテムが存在しません。");
        }
    }
}
