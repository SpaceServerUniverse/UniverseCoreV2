package space.yurisi.universecorev2.subplugins.birthdaycard.command;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.models.BirthdayMessages;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.ticket.GachaTicket;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.subplugins.birthdaycard.command.subcommand.*;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_menu.BirthdayCardMenu;
import space.yurisi.universecorev2.subplugins.birthdaycard.utils.PageJsonUtils;
import space.yurisi.universecorev2.subplugins.birthdaycard.utils.PlayerUtils;
import space.yurisi.universecorev2.subplugins.receivebox.ReceiveBoxAPI;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.NumberUtils;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.*;

public class BirthdayCardCommand implements CommandExecutor, TabCompleter {
    private final Map<String, BirthdayCardSubCommand> subCommands = Map.of(
            "register", new registerSubCommand(),
            "registerconfirm", new registerconfirmSubCommand(),
            "get", new getSubCommand(),
            "check", new checkSubCommand(),
            "list", new listSubCommand(),
            "send", new sendSubCommand(),
            "gift", new giftSubCommand()
    );

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(BirthdayCard.PREFIX + "ゲーム内で実行してね");
            return false;
        }

        if (args.length == 0) {
            BirthdayCardMenu birthdayCardMenu = new BirthdayCardMenu();
            birthdayCardMenu.sendMenu(player);
            return true;
        }

        BirthdayCardSubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            subCommand.execute(player, args);
            return true;
        }

        sendHelp(player);
        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage("""
        §6-- 🎉BirthdayCard Help --
        🎂 §bバースデーカードのコマンド一覧です 🎂
           §7/birthday : バースデーカレンダーメニューを開きます
           §7/birthday register <月> <日> : 誕生日を登録します
           §7/birthday check [プレイヤー名] : 誕生日を確認します
           §7/birthday list : 登録されている誕生日一覧
           §7/birthday get [プレイヤー名] : 誕生日カードを取得
           §7/birthday send : 誕生日メッセージを送信
           §7/birthday gift : 誕生日ギフトを受け取る
           §7/birthday help : このヘルプを表示
        """.split("\n"));
    }
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("register");
            completions.add("check");
            completions.add("list");
            completions.add("remove");
            completions.add("get");
            completions.add("send");
            completions.add("gift");
            return completions;
        }

        if (args[0].equals("register") && args.length == 2) {
            completions.add("<月(1~12)>");
            return completions;
        }

        if (args[0].equals("register") && args.length == 3) {
            try {
                int month = Integer.parseInt(args[1]);
                if (month >= 1 && month <= 12) {
                    completions.add("<日(1~31)>");
                }
            } catch (NumberFormatException ignore) {
                //無視
            }
            return completions;
        }

        if ((args[0].equals("check") || args[0].equals("get")) && args.length == 2) {
            BirthdayCardRepository repo = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);
            List<BirthdayData> birthdayData = repo.getAllBirthdayData();
            if (birthdayData.isEmpty()) {
                completions.add("<まだ登録されている人がいません>");
                return completions;
            }

            birthdayData.forEach(item -> {
                completions.add(PlayerUtils.getPlayerNameByUuid(UUID.fromString(item.getUuid())));
            });
        }

        return completions;
    }
}
