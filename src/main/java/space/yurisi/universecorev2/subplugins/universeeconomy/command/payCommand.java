package space.yurisi.universecorev2.subplugins.universeeconomy.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;

public class payCommand extends BaseCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player from_player)) {
            return false;
        }

        if (args.length != 2) {
            from_player.sendMessage(getErrorMessage("/pay <プレイヤー名> <金額>"));
            return false;
        }

        Player to_player = Bukkit.getServer().getPlayerExact(args[0]);
        if (to_player == null) {
            from_player.sendMessage(getErrorMessage("プレイヤーが見つかりませんでした。"));
            return true;
        }

        if(from_player.getName().equals(to_player.getName())){
            from_player.sendMessage(getErrorMessage("自分にはpayできません。"));
            return true;
        }

        try {
            Long amount = Long.parseLong(args[1]);
            UniverseEconomyAPI.getInstance().sendMoney(from_player, to_player, amount, "UniverseEconomy pay command 実行者:" + from_player.getName());
            from_player.sendMessage(getSuccessMessage(to_player.getName() + "さんに" + amount + this.unit + "振り込みました。"));
            to_player.sendMessage(getSuccessMessage(from_player.getName() + "さんから" + amount + this.unit + "振り込まれました。"));
        } catch (NumberFormatException e) {
            return false;
        } catch (UserNotFoundException e) {
            from_player.sendMessage(getErrorMessage("ユーザーデータが存在しないようです。管理者に報告してください。 コード-UEP1"));
        } catch (MoneyNotFoundException e) {
            from_player.sendMessage(getErrorMessage("マネーデータが存在しないようです。管理者に報告してください。 コード-UEP2"));
        } catch (CanNotAddMoneyException e) {
            from_player.sendMessage(getErrorMessage("相手のプレイヤーはお金をこれ以上持てないようです。"));
        } catch (CanNotReduceMoneyException e) {
            from_player.sendMessage(getErrorMessage("お金が足りません。"));
        } catch (ParameterException e) {
            from_player.sendMessage(getErrorMessage("お金はマイナスを指定できません。"));
        }

        return true;
    }
}
