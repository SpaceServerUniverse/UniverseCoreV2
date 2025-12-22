package space.yurisi.universecorev2.subplugins.universeguns.menu.shop_menu.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.subplugins.universeguns.core.PurchaseGun;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.ArrayList;
import java.util.List;

public class ArmorShopMenuItem extends AbstractItem {

    private String id;

    public ArmorShopMenuItem(String id){
        this.id = id;
    }

    @Override
    public ItemProvider getItemProvider() {
        CustomItem item = UniverseItem.getItem(id);
        if(item == null){
            return new ItemBuilder(Material.BARRIER).setDisplayName("不明なアイテム").addLoreLines("エラー: アイテムが見つかりません。");
        }
        return new ItemBuilder(item.getItem());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        PurchaseGun.armorPurchase(player, id);
    }
}
