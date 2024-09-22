package space.yurisi.universecorev2.subplugins.freemarket.menu.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.Market;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.UUID;

public class MarketItem extends AbstractItem {

    private final Market item;

    public MarketItem(Market item) {
        this.item = item;
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemStack itemStack = ItemStack.deserializeBytes(this.item.getSerializedItem());
        ItemBuilder itemBuilder = new ItemBuilder(itemStack)
                .addLoreLines(
                    "出品者: "+ Bukkit.getPlayer(UUID.fromString(this.item.getPlayerUuid())).getName(),
                    "金額: "+this.item.getPrice())
                .setAmount(itemStack.getAmount());
        ItemMeta meta = itemStack.getItemMeta();
        if(meta instanceof Damageable damageable){
            itemBuilder.setDamage(damageable.getDamage());
            if(damageable.hasMaxDamage()){
                itemBuilder.addLoreLines("耐久値: "+damageable.getDamage()+"/"+damageable.getMaxDamage());
            }
        }

        return itemBuilder;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        Component component = Component.text("§a[フリーマーケット]§l§n[ここをクリックで購入]")
                .clickEvent(ClickEvent.runCommand("/market buy "+ this.item.getId()))
                .hoverEvent(HoverEvent.showText(Component.text("クリックで購入")));
        player.sendMessage(component);
        inventoryClickEvent.getInventory().close();
    }
}
