package space.yurisi.universecorev2.subplugins.birthdaycard.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.NoResultException;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
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
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.models.BirthdayMessages;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.BirthdayCalendarMenu;
import space.yurisi.universecorev2.subplugins.birthdaycard.utils.PageJsonUtils;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.NumberUtils;

import java.time.DateTimeException;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BirthdayCardCommand implements CommandExecutor, TabCompleter {
    private NamespacedKey nk;
    private BirthdayCardRepository birthdayCardRepository;

    public BirthdayCardCommand() {
        nk = new NamespacedKey("universecorev2.birthday", "birthdaycard");
        this.birthdayCardRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getBirthdayCardRepository();
    }

    private boolean isValidDate(String monthArg, String dayArg, Player player) {
        if (monthArg == null || dayArg == null || !NumberUtils.isNumeric(monthArg) || !NumberUtils.isNumeric(dayArg)) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "引数が間違えているよ確認してください");
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
            //sender.sendMessage(BirthdayCard.PREFIX + "プレイヤー内で実行してね");
            return false;
        }

        if (args.length == 0) {
            BirthdayCalendarMenu menu = new BirthdayCalendarMenu();
            menu.sendMenu(player);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "register":
                if (!isValidDate(args[1], args[2], player)) return false;

                MonthDay registerMonthDay = parseMonthDay(args[1], args[2], player);
                if (registerMonthDay == null) return false;

                UUID registerPlayerUUID = player.getUniqueId();
                BirthdayData existingData = getBirthdayData(registerPlayerUUID.toString());

                if (existingData != null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "既に誕生日が登録されています");
                } else {
                    birthdayCardRepository.createBirthdayData(player, registerMonthDay);
                    Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日を登録しました");
                }

                return true;

            case "remove":
                BirthdayData removeBirthdayData = getBirthdayData(player.getUniqueId().toString());
                if (removeBirthdayData == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "削除するバースデーデータが見つかりませんでした");
                    return false;
                }

                birthdayCardRepository.deleteBirthdayData(removeBirthdayData);
                Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "自身のバースデーデータを削除しました");
                return true;

            case "get":
                if (args.length < 2) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "引数が間違えています");
                    return false;
                }
                Player birthdayPlayerToGet = Bukkit.getPlayerExact(args[1]);
                if (birthdayPlayerToGet == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "プレイヤーが見つかりません");
                    return false;
                }

                BirthdayData birthdayData = getBirthdayData(player.getUniqueId().toString());
                if (birthdayData == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "バースデーデータが見つかりません。");
                    return true;
                }
                birthdayCardRepository.createBirthdayMessage(birthdayData.getId(), player, "test");
                ItemStack writableBook = ItemStack.of(Material.WRITABLE_BOOK);
                BookMeta writableMeta = (BookMeta) writableBook.getItemMeta();
                writableMeta.displayName(Component.text("お誕生日カード (" + birthdayPlayerToGet.getName() + ")"));
                writableMeta.getPersistentDataContainer().set(nk, PersistentDataType.STRING, birthdayPlayerToGet.getUniqueId().toString());
                writableBook.setItemMeta(writableMeta);
                player.getInventory().addItem(writableBook);

                Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日カードを入手しました");
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

                Player birthdayPlayer = Bukkit.getPlayerExact(args[1]);
                if (birthdayPlayer == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "プレイヤーが見つかりません");
                    return false;
                }

                data = getBirthdayData(birthdayPlayer.getUniqueId().toString());
                if (data == null) {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, birthdayPlayer.getName() + "の誕生日が登録されていません。");
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
                            OfflinePlayer offlineBirthdayPlayer = Bukkit.getOfflinePlayer(playerUUID);
                            String playerName = offlineBirthdayPlayer.getName() != null ? offlineBirthdayPlayer.getName() : "不明なプレイヤー";

                            message.append(playerName)
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
                if (mainHandItem.getType() == Material.WRITTEN_BOOK) {
                    PersistentDataContainer container = mainHandItem.getItemMeta().getPersistentDataContainer();
                    if (container.has(nk, PersistentDataType.STRING)) {
                        String playerUuid = container.get(nk, PersistentDataType.STRING);
                        BirthdayData sendToBirthdayData = getBirthdayData(playerUuid);
                        if (sendToBirthdayData == null) {
                            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "誕生日が登録されていません");
                            return true;
                        }
                        Book book = (Book) mainHandItem.getItemMeta();
                        Bukkit.getLogger().info(book.pages().toString());
                        Message.sendSuccessMessage(player, BirthdayCard.PREFIX, playerUuid);
                        String pageJson = PageJsonUtils.serializePageJson(book.pages());
                        Bukkit.getLogger().info(pageJson);
                        birthdayCardRepository.createBirthdayMessage(sendToBirthdayData.getId(), player, pageJson);
                    } else {
                        Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday getで入手した本か確認してください");
                    }
                    return true;
                } else {
                    Message.sendErrorMessage(player, BirthdayCard.PREFIX, "著名した本にしてください");
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
                List<BirthdayMessages> birthdayMessagesList = null;
                try {
                    birthdayMessagesList = birthdayCardRepository.getBirthdayMessages(gifToBirthdayData.getId());
                } catch (BirthdayDataNotFoundException ignored) {
                    //NOOP 誕生日メッセージがない人なんていないよきっと大丈夫
                }

                gifToBirthdayData.setGiftReceived(true);
                ItemStack bookItem = ItemStack.of(Material.WRITTEN_BOOK);
                Book book = (Book) bookItem.getItemMeta();
                book.title(Component.text("お誕生日カード " + player.getName() + "さんへ"));
                book.author(Component.text("Happy Birth Day Book"));
                List<Component> pageComponents = new ArrayList<>();
                birthdayMessagesList.forEach(birthdayMessages -> {
                    pageComponents.addAll(PageJsonUtils.deserializePageJson(birthdayMessages.getMessage()));
                });
                Bukkit.getLogger().info(pageComponents.toString());
                book = book.pages(pageComponents);
                BookMeta bookMeta = (BookMeta) book;
                bookItem.setItemMeta(bookMeta);
                player.getInventory().addItem(bookItem);
                return true;
            default:
                Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "引数を間違えています");
                return false;
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
                    UUID playerUUID = UUID.fromString(birthdayData.getUuid());
                    OfflinePlayer offlineBirthdayPlayer = Bukkit.getOfflinePlayer(playerUUID);
                    String playerName = offlineBirthdayPlayer.getName() != null ? offlineBirthdayPlayer.getName() : "不明なプレイヤー";
                    completions.add(playerName);
                });
                return completions;
            }
        }
        return completions;
    }
}
