package space.yurisi.universecorev2.subplugins.universeslot.command;

import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Shelf;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Slot;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.SlotRepository;
import space.yurisi.universecorev2.exception.CannotReduceSlotCashException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.SlotNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.core.SlotCore;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotLocationManager;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SlotCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if(args.length == 0){
            Message.sendNormalMessage(player, "[スロットAI]", "----slotコマンド引数一覧----");
            Message.sendNormalMessage(player, "[スロットAI]", "/slot edit : スロット編集モードの切り替え");
            Message.sendNormalMessage(player, "[スロットAI]", "/slot register : スロットの登録");
            Message.sendNormalMessage(player, "[スロットAI]", "/slot unregister : スロットの登録解除");
            Message.sendNormalMessage(player, "[スロットAI]", "/slot info : スロット情報の確認");
            Message.sendNormalMessage(player, "[スロットAI]", "/slot config <設定値> : スロット設定値(1~6)の変更");
            Message.sendNormalMessage(player, "[スロットAI]", "/slot addCash <金額> : スロット内部キャッシュを追加");
            Message.sendNormalMessage(player, "[スロットAI]", "/slot removeCash <金額> : スロット内部キャッシュから引き出し");
            return false;
        }

        UniverseSlot main = UniverseSlot.getInstance();

        // スロット編集モードの切り替え
        if(args[0].equals("edit")) {
            if (main.getPlayerStatusManager().hasFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE)) {
                main.getPlayerStatusManager().removeFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE);
                Message.sendNormalMessage(player, "[スロットAI]", "スロット編集モードを解除しました。");
            } else {
                main.getPlayerStatusManager().addFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE);
                Message.sendSuccessMessage(player, "[スロットAI]", "スロット編集モードに入りました。");
            }
            return true;
        }

        PlayerStatusManager playerStatusManager = main.getPlayerStatusManager();
        SlotLocationManager slotLocationManager = main.getSlotLocationManager();
        SlotRepository slotRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(SlotRepository.class);

        Location clickedLocation = playerStatusManager.getClickedLocation(player.getUniqueId());
        if(clickedLocation == null) {
            Message.sendErrorMessage(player, "[スロットAI]", "スロット編集モードでスロットを右クリックしてから実行してください。");
            return false;
        }

        Slot slot;
        try{
            slot = slotRepository.getSlotFromCoordinates((long)clickedLocation.getX(), (long)clickedLocation.getY(), (long)clickedLocation.getZ(), clickedLocation.getWorld().getName());
        } catch (SlotNotFoundException e) {
            slot = null;
        }

        Block block = player.getWorld().getBlockAt(clickedLocation);
        if(!(block.getState() instanceof Shelf shelf)){
            Message.sendErrorMessage(player, "[スロットAI]", "選択した座標には棚が存在しません。");
            return false;
        }

        switch (args[0]) {

            case "register" -> {
                if (slot != null) {
                    Message.sendErrorMessage(player, "[スロットAI]", "この棚は既にスロットとして登録されています。");
                    return false;
                }

                if (!shelf.getInventory().isEmpty()) {
                    Message.sendErrorMessage(player, "[スロットAI]", "棚が空ではないためスロットにできません。");
                    return false;
                }

                try {
                    UniverseEconomyAPI.getInstance().reduceMoney(player, 10000L, "スロット設置費用");
                } catch (UserNotFoundException | MoneyNotFoundException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "ユーザーかお金の情報の取得に失敗しました。管理者にお問い合わせください。");
                    return false;
                } catch (CanNotReduceMoneyException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "お金が不足しているためスロットを設置できません。");
                    return false;
                } catch (ParameterException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "パラメーターの値が不正です。管理者にお問い合わせください。");
                    return false;
                }

                slotRepository.createSlot(player.getUniqueId(), (long) clickedLocation.getX(), (long) clickedLocation.getY(), (long) clickedLocation.getZ(), clickedLocation.getWorld().getName());
                slotLocationManager.registerSlotLocation(clickedLocation, player.getUniqueId());
                Message.sendSuccessMessage(player, "[スロットAI]", "スロットを作成しました");

                shelf.getInventory().setItem(0, UniverseSlot.getInstance().getRoller().getRandomRotateItem());
                shelf.getInventory().setItem(1, UniverseSlot.getInstance().getRoller().getRandomRotateItem());
                shelf.getInventory().setItem(2, UniverseSlot.getInstance().getRoller().getRandomRotateItem());

                return true;
            }

            case "unregister" -> {
                if (slot == null) {
                    Message.sendErrorMessage(player, "[スロットAI]", "この棚はスロットとして登録されていません。");
                    return false;
                }

                try {
                    // 以降スロット解除処理
                    if (player.isOp() || player.getUniqueId().equals(UUID.fromString(slot.getUuid()))) {
                        SlotCore slotCore = main.getPlayerStatusManager().getPlayerSlotCore(player.getUniqueId());
                        if (slotCore != null) {
                            slotCore.stopSlotMachine();
                            main.getPlayerStatusManager().removePlayerSlotCore(player.getUniqueId());
                        }
                        Long remainCash = slot.getCash();
                        if (remainCash > 0) {
                            UniverseEconomyAPI.getInstance().addMoney(player, remainCash, "スロット解除による残高返還");
                            Message.sendNormalMessage(player, "[スロットAI]", "スロット内に残っていた" + remainCash + "円を返還しました。");
                        }
                        slotLocationManager.unregisterSlotLocation(clickedLocation);
                        slotRepository.deleteSlot(slot);
                        shelf.getInventory().clear();
                        Message.sendSuccessMessage(player, "[スロットAI]", "スロットの登録を解除しました。");

                        return true;
                    }
                    User user = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUserFromUUID(UUID.fromString(slot.getUuid()));
                    Message.sendNormalMessage(player, "[スロットAI]", "他のユーザーによってこのスロットは登録されています。持ち主：" + user.getName());

                    return false;

                } catch (UserNotFoundException | MoneyNotFoundException e) {
                    return false;
                } catch (CanNotAddMoneyException e) {
                    Message.sendErrorMessage(player, "[スロットAI]", "スロット内の残高を返還できませんでした。管理者にお問い合わせください。");
                    Message.sendErrorMessage(player, "[スロットAI]", "残高: " + slot.getCash() + "円");
                    return false;
                } catch (ParameterException e) {
                    Message.sendErrorMessage(player, "[スロットAI]", "パラメーターの値が不正です。管理者にお問い合わせください。");
                    return false;
                }
            }

            case "info" -> {
                if (slot == null) {
                    Message.sendErrorMessage(player, "[スロットAI]", "この棚はスロットとして登録されていません。");
                    return false;
                }

                Message.sendNormalMessage(player, "[スロットAI]", "----スロット情報----");
                try {
                    User user = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUserFromUUID(UUID.fromString(slot.getUuid()));
                    Message.sendNormalMessage(player, "[スロットAI]", "所有者: " + user.getName());
                } catch (UserNotFoundException e) {
                    Message.sendNormalMessage(player, "[スロットAI]", "所有者: 不明 (UUID: " + slot.getUuid() + ")");
                }

                if (player.getUniqueId().equals(UUID.fromString(slot.getUuid()))) {
                    Message.sendNormalMessage(player, "[スロットAI]", "内部キャッシュ: " + slot.getCash() + "円");
                    Message.sendNormalMessage(player, "[スロットAI]", "設定: " + slot.getConfig());
                }
                return true;
            }

            case "config" -> {
                if (args.length != 2) {
                    Message.sendErrorMessage(player, "[スロットAI]", "引数が不正です。/slot config <設定値>の形式で実行してください。");
                    return false;
                }

                int config;
                try {
                    config = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    Message.sendErrorMessage(player, "[スロットAI]", "設定値は数値で指定してください。");
                    return false;
                }

                if (config <= 0 || config > 6) {
                    Message.sendErrorMessage(player, "[スロットAI]", "設定値は1~6の範囲で指定してください。");
                    return false;
                }

                if (slot == null) {
                    Message.sendErrorMessage(player, "[スロットAI]", "この棚はスロットとして登録されていません。");
                    return false;
                }

                if (!player.getUniqueId().equals(UUID.fromString(slot.getUuid()))) {
                    Message.sendErrorMessage(player, "[スロットAI]", "このスロットの所有者ではないため、設定を変更できません。");
                    return false;
                }

                slotRepository.updateConfig(slot, config);
                Message.sendSuccessMessage(player, "[スロットAI]", "スロットの設定を " + config + " に変更しました。");
                return true;
            }

            case "addCash" -> {
                if (args.length != 2) {
                    Message.sendErrorMessage(player, "[スロットAI]", "引数が不正です。/slot addCash <金額>の形式で実行してください。");
                    return false;
                }

                long amount;
                try {
                    amount = Long.parseLong(args[1]);
                } catch (NumberFormatException e) {
                    Message.sendErrorMessage(player, "[スロットAI]", "金額は数値で指定してください。");
                    return false;
                }

                if (amount <= 0) {
                    Message.sendErrorMessage(player, "[スロットAI]", "金額は正の整数で指定してください。");
                    return false;
                }

                if (slot == null) {
                    Message.sendErrorMessage(player, "[スロットAI]", "この棚はスロットとして登録されていません。");
                    return false;
                }

                if (!player.getUniqueId().equals(UUID.fromString(slot.getUuid()))) {
                    Message.sendErrorMessage(player, "[スロットAI]", "このスロットの所有者ではないため、キャッシュを追加できません。");
                    return false;
                }

                try {
                    slotRepository.updateCash(slot, amount);
                    UniverseEconomyAPI.getInstance().reduceMoney(player, amount, "スロット内部キャッシュ追加");
                } catch (UserNotFoundException | MoneyNotFoundException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "ユーザーかお金の情報の取得に失敗しました。管理者にお問い合わせください。");
                    return false;
                } catch (CanNotReduceMoneyException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "お金が不足しているためスロットにキャッシュを追加できません。");
                    return false;
                } catch (ParameterException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "パラメーターの値が不正です。管理者にお問い合わせください。");
                    return false;
                } catch (CannotReduceSlotCashException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "スロットのキャッシュを追加できません。管理者にお問い合わせください。");
                    return false;
                }

                Message.sendSuccessMessage(player, "[スロットAI]", "スロットの内部キャッシュに " + amount + " 円を追加しました。");
                Message.sendSuccessMessage(player, "[スロットAI]", "現在のスロット内部キャッシュ: " + slot.getCash() + " 円");

                return true;
            }
            case "removeCash" -> {
                if (args.length != 2) {
                    Message.sendErrorMessage(player, "[スロットAI]", "引数が不正です。/slot removeCash <金額>の形式で実行してください。");
                    return false;
                }
                long amount;
                try {
                    amount = Long.parseLong(args[1]);
                } catch (NumberFormatException e) {
                    Message.sendErrorMessage(player, "[スロットAI]", "金額は数値で指定してください。");
                    return false;
                }

                if (amount <= 0) {
                    Message.sendErrorMessage(player, "[スロットAI]", "金額は正の整数で指定してください。");
                    return false;
                }

                if (slot == null) {
                    Message.sendErrorMessage(player, "[スロットAI]", "この棚はスロットとして登録されていません。");
                    return false;
                }

                if (!player.getUniqueId().equals(UUID.fromString(slot.getUuid()))) {
                    Message.sendErrorMessage(player, "[スロットAI]", "このスロットの所有者ではないため、キャッシュを引き出せません。");
                    return false;
                }

                try {
                    slotRepository.updateCash(slot, -amount);
                    UniverseEconomyAPI.getInstance().addMoney(player, amount, "スロット内部キャッシュ引き出し");
                } catch (UserNotFoundException | MoneyNotFoundException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "ユーザーかお金の情報の取得に失敗しました。管理者にお問い合わせください。");
                    return false;
                } catch (CanNotAddMoneyException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "スロットのキャッシュから引き出せません。管理者にお問い合わせください。");
                    return false;
                } catch (ParameterException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "パラメーターの値が不正です。管理者にお問い合わせください。");
                    return false;
                } catch (CannotReduceSlotCashException exception) {
                    Message.sendErrorMessage(player, "[スロットAI]", "スロットのキャッシュが不足しているため、引き出せません。");
                    return false;
                }
                Message.sendSuccessMessage(player, "[スロットAI]", "スロットの内部キャッシュから " + amount + " 円を引き出しました。");
                Message.sendSuccessMessage(player, "[スロットAI]", "現在のスロット内部キャッシュ: " + slot.getCash() + " 円");
                if(slot.getCash() < 5000L){
                    Message.sendWarningMessage(player, "[スロットAI]", "スロット内部キャッシュが5000円未満です。このスロットはプレイ不可能になります。");
                }
                return true;

            }
            default -> {
                Message.sendErrorMessage(player, "[スロットAI]", "不明な引数です。/slotコマンドの引数一覧を確認してください。");
                return false;
            }
        }

    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = List.of("edit", "register", "unregister", "info", "config", "addCash", "removeCash");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }
        return ImmutableList.of();
    }
}
