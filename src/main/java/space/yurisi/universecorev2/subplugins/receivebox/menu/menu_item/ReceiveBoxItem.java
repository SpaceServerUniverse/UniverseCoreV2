package space.yurisi.universecorev2.subplugins.receivebox.menu.menu_item;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.ReceiveBox;
import space.yurisi.universecorev2.subplugins.receivebox.menu.ReceiveBoxMenu;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.Sound;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiveBoxItem extends AbstractItem {
    ReceiveBox receiveBox;

    public ReceiveBoxItem(ReceiveBox receiveBox) {
        this.receiveBox = receiveBox;
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemStack itemStack = ItemStack.deserializeBytes(receiveBox.getSerializedItem());
        Date date = this.receiveBox.getExpired_at();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
        String formattedDate = sdf.format(date);

        return new ItemBuilder(itemStack)
                .addLoreLines(
                        this.receiveBox.getDescription(),
                        "期限: §c" + formattedDate)
                .setAmount(itemStack.getAmount());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        if (player.getInventory().firstEmpty() != -1) {
            this.receiveBox.setReceived(true);
            UniverseCoreV2API.getInstance().getDatabaseManager().getReceiveBoxRepository().updateReceiveBox(this.receiveBox);
            Sound.sendSuccessSound(player);
            Message.sendSuccessMessage(player, "[管理AI]", "アイテムを受け取りました！");
            player.getInventory().addItem(ItemStack.deserializeBytes(this.receiveBox.getSerializedItem()));
        }else{
            Message.sendErrorMessage(player, "[管理AI]", "アイテムがいっぱいです！");
        }

        new ReceiveBoxMenu().sendMenu(player);
    }
}
