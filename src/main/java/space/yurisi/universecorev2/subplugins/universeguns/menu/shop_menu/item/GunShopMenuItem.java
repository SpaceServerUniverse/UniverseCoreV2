package space.yurisi.universecorev2.subplugins.universeguns.menu.shop_menu.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.universeguns.core.PurchaseGun;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.ArrayList;
import java.util.List;

public class GunShopMenuItem extends AbstractItem {

    private final Gun gun;

    public GunShopMenuItem(Gun gun) {
        this.gun = gun;
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta != null) {
            itemMeta.setItemModel(new NamespacedKey(UniverseCoreV2.getInstance(), gun.getTextureID()));
            itemMeta.setDisplayName(gun.getName());
            List<Component> lore = new ArrayList<>(gun.getGunComponents());
            lore.add(Component.text(gun.getFlavorText()));
            lore.add(Component.text("§7値段: §b" + gun.getPrice() + "§7枚"));
            lore.add(Component.text("§7クリックで購入"));
            itemMeta.lore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        return new ItemBuilder(itemStack);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        PurchaseGun.executePurchase(player, gun);
    }
}
