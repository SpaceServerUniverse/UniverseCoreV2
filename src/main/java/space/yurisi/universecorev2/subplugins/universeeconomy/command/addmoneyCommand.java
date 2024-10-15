package space.yurisi.universecorev2.subplugins.universeeconomy.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.api.LuckPermsWrapper;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;

import static java.lang.Long.parseLong;

public class addmoneyCommand extends BaseCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!LuckPermsWrapper.isUserInAdminOrDevGroup(player)) {
            player.sendMessage(getErrorMessage("このコマンドを実行する権限がありません。"));
            return true;
        }

        if (args.length != 2) {
            player.sendMessage(getErrorMessage("/addmoney <プレイヤー名> <金額>"));
            return false;
        }

        Player to_player = Bukkit.getServer().getPlayerExact(args[0]);

        if (to_player == null) {
            player.sendMessage(getErrorMessage("ユーザーが見つかりませんでした。サーバー内に存在しますか？"));
            return true;
        }

        try {
            Long amount = parseLong(args[1]);
            UniverseEconomyAPI.getInstance().addMoney(to_player, amount, "UniverseEconomy addmoney command 実行者:" + player.getName());
            player.sendMessage(getSuccessMessage(to_player.getName() + "のお金を" + amount + unit + "追加しました。"));
        } catch (NumberFormatException e) {
            return false;
        } catch (UserNotFoundException e) {
            player.sendMessage(getErrorMessage("ユーザーデータが存在しないようです。管理者に報告してください。 コード-UEAD1"));
        } catch (MoneyNotFoundException e) {
            player.sendMessage(getErrorMessage("マネーデータが存在しないようです。管理者に報告してください。 コード-UEAD2"));
        } catch (CanNotAddMoneyException e) {
            player.sendMessage(getErrorMessage("プレイヤーに与えるお金が多すぎます。桁数を確認してください。"));
        } catch (ParameterException e) {
            player.sendMessage(getErrorMessage("お金はマイナスを指定できません。"));
        }
        return true;
    }
}
