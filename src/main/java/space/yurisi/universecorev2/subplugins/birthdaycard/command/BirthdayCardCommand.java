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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BirthdayCardCommand implements CommandExecutor, TabCompleter {
    private final NamespacedKey nk;
    private final BirthdayCardRepository birthdayCardRepository;
    List<String> birthdayMessages = Arrays.asList(
            "お誕生日おめでとう！今日は特別な日だから、素敵なことがたくさんありますように！",
            "ハッピーバースデー！どんな日になるか楽しみだね。素晴らしい一年にしよう！",
            "お誕生日おめでとう！あなたの笑顔がもっと見られる一年になりますように！",
            "お誕生日おめでとうございます。新しい一年が素晴らしい成長と幸福に満ちたものとなりますように。",
            "この特別な日を迎えられたことを心よりお祝い申し上げます。素晴らしい一年になりますように。",
            "お誕生日おめでとうございます。今後の一年が健康で幸せに満ちたものでありますよう、心より願っております。",
            "お誕生日おめでとう！年齢はただの数字…でも、ケーキの数は本物だよ！",
            "ハッピーバースデー！年を取ることは避けられないけれど、心はいつまでも若々しく！",
            "お誕生日おめでとう！歳を重ねるのも悪くない、特にケーキがあるときはね！"
    );

    public BirthdayCardCommand() {
        nk = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.BIRTHDAY_CARD);
        this.birthdayCardRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getBirthdayCardRepository();
    }

    private boolean isValidDate(String monthArg, String dayArg, Player player) {
        if (monthArg == null || dayArg == null || !NumberUtils.isNumeric(monthArg) || !NumberUtils.isNumeric(dayArg)) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday register <月> <日>");
            return false;
        }
        return true;
    }

    private MonthDay parseMonthDay(String monthArg, String dayArg, Player player) {
        int month = Integer.parseInt(monthArg);
        int day = Integer.parseInt(dayArg);
        try {
            return MonthDay.of(month, day);
        } catch (DateTimeException e) {
            Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "月または日が無効です。確認してください。");
            return null;
        }
    }

    private BirthdayData getBirthdayData(String playerUuid) {
        try {
            return birthdayCardRepository.getBirthdayData(playerUuid);
        } catch (BirthdayDataNotFoundException e) {
            return null;
        }
    }

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
        switch (args[0].toLowerCase()) {
            case "register":
                if (args.length < 3) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday register <月> <日>");
                    return false;
                }
                if (!isValidDate(args[1], args[2], player)) return false;

                MonthDay registerMonthDay = parseMonthDay(args[1], args[2], player);
                UUID registerPlayerUUID = player.getUniqueId();
                if (registerMonthDay == null) return true;

                BirthdayData existingData = getBirthdayData(registerPlayerUUID.toString());

                if (existingData != null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "既に誕生日が登録されています");
                } else {

                    Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "一度登録すると変更することはできません");
                    Message.sendNormalMessage(player, BirthdayCard.PREFIX, "[登録]", ClickEvent.runCommand("/birthday registerconfirm " + registerMonthDay.getMonthValue() + " " + registerMonthDay.getDayOfMonth()), "誕生日を登録します");
                }
                return true;
            case "registerconfirm":
                if (args.length < 3) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday register <月> <日>");
                    return false;
                }

                if (!isValidDate(args[1], args[2], player)) return false;

                MonthDay registerConfirmMonthDay = parseMonthDay(args[1], args[2], player);
                if (registerConfirmMonthDay == null) return true;

                UUID registerConfirmPlayerUUID = player.getUniqueId();
                BirthdayData registerConfirmexistingData = getBirthdayData(registerConfirmPlayerUUID.toString());

                if (registerConfirmexistingData != null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "既に誕生日が登録されています");
                } else {
                    birthdayCardRepository.createBirthdayData(player, registerConfirmMonthDay);
                    Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日を登録しました");
                }

                return true;
            case "get":
                if (args.length < 2) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday get <プレイヤー名>");
                    return false;
                }
                OfflinePlayer birthdayPlayerToGet = Bukkit.getOfflinePlayer(args[1]);
                if (birthdayPlayerToGet == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "プレイヤーが見つかりません");
                    return true;
                }
                BirthdayData birthdayData = getBirthdayData(birthdayPlayerToGet.getUniqueId().toString());
                if (birthdayData == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "バースデーデータが見つかりません");
                    return true;
                }
                if (birthdayData.getUuid().equals(player.getUniqueId().toString())) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "自分自身にメッセージを書くことはできません");
                    return true;
                }
                ItemStack writableBook = ItemStack.of(Material.WRITABLE_BOOK);
                BookMeta writableMeta = (BookMeta) writableBook.getItemMeta();
                writableMeta.displayName(Component.text("お誕生日カード (" + birthdayPlayerToGet.getName() + ")"));
                writableMeta.getPersistentDataContainer().set(nk, PersistentDataType.STRING, birthdayPlayerToGet.getUniqueId().toString());
                writableBook.setItemMeta(writableMeta);
                player.getInventory().addItem(writableBook);

                Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日カードを入手しました");
                Message.sendNormalMessage(player, BirthdayCard.PREFIX, "[送る]", ClickEvent.runCommand("/birthday send"), "お誕生日カードを送る");
                return true;

            case "check":
                BirthdayData data;
                if (args.length < 2) {
                    data = getBirthdayData(player.getUniqueId().toString());
                    if (data == null) {
                        Message.sendErrorMessage(player, BirthdayCard.PREFIX, "あなたの誕生日が登録されていません");
                        return true;
                    }
                    Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "あなたの誕生日は" + data.getMonth() + "月" + data.getDay() + "日");
                    return true;
                }

                OfflinePlayer birthdayPlayer = Bukkit.getOfflinePlayer(args[1]);
                if (birthdayPlayer == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "プレイヤーが見つかりません");
                    return true;
                }
                data = getBirthdayData(birthdayPlayer.getUniqueId().toString());
                if (data == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, birthdayPlayer.getName() + "の誕生日が登録されていません");
                    return true;
                }
                Message.sendSuccessMessage(player, BirthdayCard.PREFIX, birthdayPlayer.getName() + "の誕生日は" + data.getMonth() + "月" + data.getDay() + "日");
                return true;

            case "list":
                try {
                    List<BirthdayData> birthdayDataList = birthdayCardRepository.getAllBirthdayData();
                    if (birthdayDataList.isEmpty()) {
                        Message.sendNormalMessage(player, BirthdayCard.PREFIX, "まだ誕生日が登録されていません");
                    } else {
                        StringBuilder message = new StringBuilder("登録されている誕生日:\n");
                        for (BirthdayData bd : birthdayDataList) {
                            UUID playerUUID = UUID.fromString(bd.getUuid());
                            message.append(PlayerUtils.getPlayerNameByUuid(playerUUID))
                                    .append("の誕生日は")
                                    .append(bd.getMonth()).append("月")
                                    .append(bd.getDay()).append("日\n");
                        }
                        Message.sendNormalMessage(player, BirthdayCard.PREFIX, message.toString());
                    }
                } catch (BirthdayDataNotFoundException e) {
                    Message.sendNormalMessage(player, BirthdayCard.PREFIX, "誕生日データが見つかりませんでした");
                }
                return true;

            case "send":
                ItemStack mainHandItem = player.getInventory().getItemInMainHand();
                if (mainHandItem.getType() != Material.WRITTEN_BOOK) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "著名した本にしてください");
                    return true;
                }
                PersistentDataContainer container = mainHandItem.getItemMeta().getPersistentDataContainer();
                if (container.has(nk, PersistentDataType.STRING)) {
                    String playerUuid = container.get(nk, PersistentDataType.STRING);
                    BirthdayData sendToBirthdayData = getBirthdayData(playerUuid);
                    if (sendToBirthdayData == null) {
                        Message.sendErrorMessage(player, BirthdayCard.PREFIX, "誕生日が登録されていません");
                        return true;
                    }
                    if (sendToBirthdayData.getUuid().equals(player.getUniqueId().toString())) {
                        Message.sendErrorMessage(player, BirthdayCard.PREFIX, "自分自身にメッセージを送信することはできません");
                        return true;
                    }
                    Date expire_date = new Date(
                            System.currentTimeMillis() + Duration.ofDays(10).toMillis()
                    );

                    Book book = (Book) mainHandItem.getItemMeta();
                    Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日カードを送信しました");
                    player.getInventory().remove(mainHandItem);
                    Component lastPage = book.pages().get(book.pages().size() - 1);
                    Component updatedLastPage = lastPage.append(Component.text("\n" + player.getName() + " より"));
                    List<Component> newPages = new ArrayList<>(book.pages());
                    newPages.set(newPages.size() - 1, updatedLastPage);
                    String pageJson = PageJsonUtils.serializePageJson(newPages);
                    var sendToBirthdayMessages = birthdayCardRepository.createBirthdayMessage(sendToBirthdayData.getId(), player, pageJson);
                    if (birthdayCardRepository.canReceiveGachaTicket(sendToBirthdayMessages)) {
                        ItemStack ticket = UniverseItem.getItem(GachaTicket.id).getItem();
                        ticket.setAmount(5);
                        Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日カードを書いてくれてありがとう\nガチャチケを5枚プレゼント!!");
                        if (player.getInventory().firstEmpty() == -1) {
                            Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "インベントリーがいっぱいなので\n報酬受け取りボックスに追加しました");
                            ReceiveBoxAPI.AddReceiveItem(ticket, player.getUniqueId(), expire_date, "お誕生日カードを書いてくれたから(インベントリーがいっぱい)");
                        } else {
                            player.getInventory().addItem(ticket);
                        }
                        sendToBirthdayMessages.setReceivedGachaTicket(true);
                        birthdayCardRepository.updateBirthdayMessage(sendToBirthdayMessages);
                    }
                } else {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday getで入手した本か確認してください");
                }
                return true;
            case "gift":
                BirthdayData gifToBirthdayData = getBirthdayData(player.getUniqueId().toString());
                if (gifToBirthdayData == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "誕生日が登録されていません");
                    return true;
                }
                if (gifToBirthdayData.isGiftReceived()) {
                    Message.sendNormalMessage(player, BirthdayCard.PREFIX, "もうすでに誕生日カードを受け取っています");
                    return true;
                }
                LocalDate thisYearBirthday = LocalDate.of(LocalDate.now().getYear(), gifToBirthdayData.getMonth(), gifToBirthdayData.getDay());
                LocalDate today = LocalDate.now();
                if (!thisYearBirthday.isEqual(today)) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "まだ誕生日じゃないよ。誕生日を確認して、後で戻ってきてね！");
                    return true;
                }
                if (player.getInventory().firstEmpty() == -1) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "インベントリーがいっぱいです");
                    Message.sendNormalMessage(player, BirthdayCard.PREFIX, "[受け取る]", ClickEvent.runCommand("/birthday gift"), "お誕生日カードを受け取る");
                    return true;
                }
                List<BirthdayMessages> birthdayMessagesList = null;
                try {
                    birthdayMessagesList = birthdayCardRepository.getBirthdayMessages(gifToBirthdayData.getId());
                } catch (BirthdayDataNotFoundException ignored) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "エラーが発生しました。");
                    return true;
                }
                Date expire_date = new Date(
                        System.currentTimeMillis() + Duration.ofDays(10).toMillis()
                );

                gifToBirthdayData.setGiftReceived(true);
                birthdayCardRepository.updateBirthdayData(gifToBirthdayData);
                ItemStack bookItem = ItemStack.of(Material.WRITTEN_BOOK);
                Book book = (Book) bookItem.getItemMeta();
                book.title(Component.text("お誕生日カード " + player.getName() + "さんへ"));
                book.author(Component.text("お誕生日カード (" + LocalDate.now().getYear() + ")")
                        .color(NamedTextColor.GOLD)
                        .decorate(TextDecoration.BOLD));
                List<Component> pageComponents = new ArrayList<>();
                if (birthdayMessagesList == null) {
                    Random random = new Random();
                    int numberOfMessages = random.nextInt(birthdayMessages.size() + 1);
                    Collections.shuffle(birthdayMessages);
                    List<String> selectedMessages = birthdayMessages.subList(0, numberOfMessages);
                    for (String message : selectedMessages) {
                        pageComponents.add(Component.text(message));
                    }
                    Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日カードをお贈りしました");
                } else {
                    birthdayMessagesList.forEach(birthdayMessages -> {
                        Bukkit.getLogger().info(PageJsonUtils.deserializePageJson(birthdayMessages.getMessage()).toString());
                        pageComponents.addAll(PageJsonUtils.deserializePageJson(birthdayMessages.getMessage()));
                        birthdayCardRepository.deleteBirthdayMessage(birthdayMessages);
                    });
                }
                book = book.pages(pageComponents);
                BookMeta bookMeta = (BookMeta) book;
                bookItem.setItemMeta(bookMeta);
                player.getInventory().addItem(bookItem);
                ItemStack ticket = UniverseItem.getItem(GachaTicket.id).getItem();
                ticket.setAmount(10);
                ReceiveBoxAPI.AddReceiveItem(ticket, player.getUniqueId(), expire_date, "お誕生日プレゼント");
                Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "ガチャチケ10枚プレゼント");
                Bukkit.getServer().broadcast(Component.text("🎉 今日は ", NamedTextColor.YELLOW)
                        .append(Component.text(player.getName(), NamedTextColor.GOLD))
                        .append(Component.text(" さんの誕生日です！🎂\n", NamedTextColor.YELLOW))
                        .append(Component.text("素晴らしい一年になりますように！おめでとう！", NamedTextColor.GREEN)));
                return true;
            default:
                String[] helpMessage = """
                        §6-- 🎉BirthdayCard Help --
                        🎂 §bバースデーカードのコマンド一覧です 🎂
                           §7/birthday : バースデーカレンダーメニューを開きます
                           §7/birthday register <月> <日> : 誕生日を登録します (一度登録すると変更できません)
                           §7/birthday check [プレイヤー名] : 自分または指定したプレイヤーの誕生日を確認します
                           §7/birthday list : 登録されている誕生日の一覧を表示します
                           §7/birthday get [プレイヤー名] : 指定したプレイヤーに送る誕生日カードを取得します
                           §7/birthday send : 手持ちの署名済み本をバースデーメッセージとして送信します
                           §7/birthday gift : 誕生日ギフトを受け取ります（誕生日当日限定一回のみ）
                           §7/birthday help : このヘルプを表示します
                        """.split("\n");
                player.sendMessage(helpMessage);
                break;
        }
        return true;
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

        if (args[0].equals("register")) {
            if (args.length == 2) {
                completions.add("<月(1~12)>");
                return completions;
            } else if (args.length == 3) {
                try {
                    int month = Integer.parseInt(args[1]);
                    if (month >= 1 && month <= 12) {
                        completions.add("<日(1~31)>");
                    }
                } catch (NumberFormatException e) {
                }
                return completions;
            }
        }
        if (args[0].equals("check") || args[0].equals("get")) {
            if (args.length == 2) {
                List<BirthdayData> birthdayDatas;
                try {
                    birthdayDatas = birthdayCardRepository.getAllBirthdayData();
                } catch (BirthdayDataNotFoundException error) {
                    completions.add("<まだ登録されている人がいません>");
                    return completions;
                }
                birthdayDatas.forEach(birthdayData -> {
                    completions.add(PlayerUtils.getPlayerNameByUuid(UUID.fromString(birthdayData.getUuid())));
                });
                return completions;
            }
        }
        return completions;
    }
}
