package space.yurisi.universecorev2.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.book.MainMenuBook;
import space.yurisi.universecorev2.menu.MainMenu;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class menuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        switch (args.length) {
            case 0:
                MainMenu mainMenu = new MainMenu();
                mainMenu.sendMenu(player);

                if (new Random().nextDouble() < 0.000122) {
                    player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 10, 1);
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                    Message.sendSuccessMessage(player, "[管理AI]", "§c§oメニューを開きました...ヮ! クリーパーが... (´・ω・`)");
                    return true;
                }

                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_FALL, 10, 1);
                Message.sendSuccessMessage(player, "[管理AI]", "メニューを開きました");
                return true;
            case 1:
                if (!args[0].equals("book")) {
                    Message.sendWarningMessage(player, "[管理AI]", "メインメニュー用のアイテムを入手するには [/menu book] と入力してください。");
                    return false;
                }

                CustomItem item = UniverseItem.getItem(MainMenuBook.id);
                Inventory inv = player.getInventory();
                if (inv.firstEmpty() == -1) {
                    Message.sendErrorMessage(player, "[管理AI]", "インベントリがいっぱいです。");
                    return false;
                }

                if (item == null) {
                    Message.sendErrorMessage(player, "[管理AI]", "不明なエラーが発生しました。運営に報告してください。通常通りのメニューは [/menu] で開けます。");
                    return false;
                }

                ItemStack itemStack = item.getItem();
                if (inv.contains(item.getItem())) {
                    Message.sendErrorMessage(player, "[管理AI]", "既にメインメニュー用のアイテムを持っています。");
                    return false;
                }

                inv.addItem(itemStack);

                Message.sendSuccessMessage(player, "[管理AI]", "メインメニュー用のアイテムをインベントリに追加しました");
                return true;
            default:
                Message.sendNormalMessage(player, "[管理AI]", "[/menu] でメインメニューを開くか、[/menu book] でメインメニュー用のアイテムを入手できます。");
                return true;
        }
    }
}
