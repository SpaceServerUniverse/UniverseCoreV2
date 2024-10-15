package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_player_list_menu.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.birthdaycard.utils.PlayerUtils;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.UUID;

public class PlayerHeadItem extends AbstractItem {
    private UUID playerUuid;

    public PlayerHeadItem(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }



    public ItemProvider getItemProvider() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUuid);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(offlinePlayer);
        head.setItemMeta(meta);
        return new ItemBuilder(head).setDisplayName(PlayerUtils.getPlayerNameByUuid(playerUuid));
    }

    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.performCommand("birthday get " + PlayerUtils.getPlayerNameByUuid(playerUuid));
        player.closeInventory();
    }
}
