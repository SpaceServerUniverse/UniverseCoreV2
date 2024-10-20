package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_calendar_menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_player_list_menu.BirthdayPlayerListMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.List;

public class PlayerListBirthdayCardMenuItem extends AbstractItem {
    List<BirthdayData> birthdayDataList;

    public PlayerListBirthdayCardMenuItem(List<BirthdayData> birthdayDataList) {
        this.birthdayDataList = birthdayDataList;
    }

    @Override
    public ItemProvider getItemProvider() {
        if (birthdayDataList.isEmpty()) {
            return new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("プレイヤーリスト").addLoreLines("まだ登録されていません");
        } else {
            return new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("プレイヤーリスト");
        }

    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if (!birthdayDataList.isEmpty()) {
            BirthdayPlayerListMenu birthdayPlayerListMenu = new BirthdayPlayerListMenu(birthdayDataList);
            birthdayPlayerListMenu.sendMenu(player);
        }
    }
}
