package space.yurisi.universecorev2.subplugins.universeeconomy.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;

public class seemoneyCommand extends BaseCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(getErrorMessage("/seemoney <プレイヤー名>"));
            return false;
        }

        try {
            Long money = UniverseEconomyAPI.getInstance().getMoneyFromUserName(args[0]);
            player.sendMessage(getSuccessMessage(args[0] + "の所持金 :" + money.toString() + this.unit));
        } catch (UserNotFoundException e) {
            player.sendMessage(getErrorMessage("プレイヤーが見つかりませんでした。"));
        } catch (MoneyNotFoundException e) {
            player.sendMessage(getErrorMessage("プレイヤーのマネーデータが見つかりませんでした。"));
        }
        return true;
    }
}
