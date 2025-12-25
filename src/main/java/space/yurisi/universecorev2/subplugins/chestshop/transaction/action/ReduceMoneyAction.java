package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.TransactionException;
import space.yurisi.universecorev2.subplugins.chestshop.utils.InventoryUtils;
import space.yurisi.universecorev2.subplugins.chestshop.utils.ItemUtils;
import space.yurisi.universecorev2.subplugins.chestshop.utils.SuperMessageHelper;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;

public class ReduceMoneyAction implements AtomicRollbackableAction {
    private @NotNull UniverseEconomyAPI api;
    private @NotNull Player player;
    private @NotNull Long price;
    private @NotNull String reason;

    public ReduceMoneyAction(
            @NotNull UniverseEconomyAPI api,
            @NotNull Player player,
            @NotNull Long price,
            @NotNull String reason
    ) {
        this.api = api;
        this.player = player;
        this.price = price;
        this.reason = reason;
    }

    @Override
    public @NotNull RollbackFunc execute() throws TransactionException {
        try {
            api.reduceMoney(player, price, reason);
        } catch (UserNotFoundException | MoneyNotFoundException | ParameterException e) {
            throw new TransactionException("システムエラーです", "user or money not found");
        } catch (CanNotReduceMoneyException e) {
            throw new TransactionException("お金が不足しています", "insufficient money");
        }

        return () -> {
            try {
                api.addMoney(player, price, "[組戻し]" + reason);
            } catch (Exception e) {
                throw new TransactionException("組戻しに失敗しました。管理者に連絡してください", "failed to rollback reducing money action", e);
            }
        };
    }
}
