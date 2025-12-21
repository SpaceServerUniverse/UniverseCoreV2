package space.yurisi.universecorev2.subplugins.loginbonus.command;

import org.apache.commons.lang3.time.DateUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.LoginBonus;
import space.yurisi.universecorev2.database.repositories.LoginBonusRepository;
import space.yurisi.universecorev2.exception.LoginBonusNotFoundException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.loginbonus.menu.LoginBonusCalendar;
import space.yurisi.universecorev2.subplugins.loginbonus.utils.LoginBonusType;
import space.yurisi.universecorev2.subplugins.mywarp.utils.MessageHelper;
import space.yurisi.universecorev2.subplugins.receivebox.ReceiveBoxAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.Sound;

import java.util.*;

public class LoginBonusCommand implements CommandExecutor, TabCompleter {

    LoginBonusRepository loginBonusRepository;

    public LoginBonusCommand() {
        loginBonusRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getLoginBonusRepository();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length == 0) {
            LoginBonusCalendar menu = new LoginBonusCalendar();
            menu.sendMenu(player);
            return false;
        }

        if (args[0].equals("receive")) {
            Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

            LoginBonus loginBonus;
            try {
                loginBonus = loginBonusRepository.getLoginBonusByPlayerAndDate(player, today);
                if (Boolean.TRUE.equals(loginBonus.getIs_received())) {
                    Message.sendErrorMessage(player, "[ログインAI]", "すでに受け取っています!");
                    return true;
                }
            } catch (LoginBonusNotFoundException ignored) {
                loginBonus = loginBonusRepository.createLoginBonus(player);
            }

            LoginBonusType loginBonusType = new LoginBonusType();
            Calendar calendar = Calendar.getInstance(); // 今日
            ItemStack bonusItem = loginBonusType.getItemStackByCalendar(calendar);

            if (bonusItem == null || bonusItem.getType() == Material.BARRIER) {
                Message.sendErrorMessage(player, "[ログインAI]", "エラーが発生しました。errorcode:1");
                return true;
            }

            loginBonus.setIs_received(true);
            loginBonusRepository.update(loginBonus);
            Sound.sendSuccessSound(player);

            if (bonusItem.getType() == Material.GOLD_NUGGET) {
                try {
                    UniverseEconomyAPI.getInstance().addMoney(player, 10000L);
                    Message.sendSuccessMessage(player, "[銀行AI] ", "ログインボーナス様から10000円振り込まれました！");
                } catch (UserNotFoundException | MoneyNotFoundException | CanNotAddMoneyException |
                         ParameterException ex) {
                    loginBonus.setIs_received(false);
                    loginBonusRepository.update(loginBonus);

                    Message.sendErrorMessage(player, "[銀行AI]", "エラーが発生しました。errorcode:2");
                }
                return true;
            }

            Calendar expireDate = Calendar.getInstance();
            expireDate.add(Calendar.MONTH, 1);
            ReceiveBoxAPI.AddReceiveItem(bonusItem, player.getUniqueId(), expireDate.getTime(), "ログインボーナス");

            Message.sendSuccessMessage(player, "[ログインAI]", "§a受け取りボックスに送られました！");
            return true;
        }

        String[] helpMessage = """
                     /loginbonus ログインボーナス画面を開く
                     /loginbonus receive ログインボーナスを受け取る
                """.split("\n");
        sender.sendMessage(helpMessage);
        return true;
    }

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("loginbonus")) {
            return null;
        }

        if (args.length == 1) {
            String input = args[0].toLowerCase();

            List<String> options = List.of("receive");

            List<String> result = new ArrayList<>();
            for (String option : options) {
                if (option.startsWith(input)) {
                    result.add(option);
                }
            }

            return result;
        }

        return List.of();
    }
}
