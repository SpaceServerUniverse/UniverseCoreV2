package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.InterruptTransactionException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;

import java.util.function.Consumer;

/**
 * 指定したプレイヤーの口座から指定額を引き落とすアクション
 */
public class WithdrawMoneyAction implements AtomicRollbackableAction {
    private final @NotNull UniverseEconomyAPI api;
    private final @NotNull Player player;
    private final @NotNull Long price;
    private final @NotNull String reason;

    public record MissingAccountContext(){}
    public record InsufficientBalanceContext(){}
    public record ExceededBalanceContext(){}

    private @NotNull Consumer<MissingAccountContext> whenMissingAccountHandler = (e) -> {};
    private @NotNull Consumer<InsufficientBalanceContext> whenInsufficientBalanceHandler = (e) -> {};
    private @NotNull Consumer<MissingAccountContext> whenRollbackMissingAccountHandler = (e) -> {};
    private @NotNull Consumer<ExceededBalanceContext> whenRollbackExceededBalanceHandler = (e) -> {};

    /**
     * 引き出しアクションを構築する
     * @param player 対象のプレイヤー
     * @param price 金額(>0)
     * @param reason 引き落とし理由
     */
    public WithdrawMoneyAction(
            @NotNull UniverseEconomyAPI api,
            @NotNull Player player,
            @NotNull Long price,
            @NotNull String reason
    ) {
        if(price <= 0) {
            throw new IllegalArgumentException("price must be greater than 0");
        }
        this.api = api;
        this.player = player;
        this.price = price;
        this.reason = reason;
    }

    /**
     * 適用時にユーザーの口座が見つからなかった場合に実行されるハンドラーを指定する
     * @param handler 呼び出し対象
     * @return this
     */
    public @NotNull WithdrawMoneyAction whenMissingAccount(@NotNull Consumer<MissingAccountContext> handler) {
        whenMissingAccountHandler = handler;
        return this;
    }

    /**
     * 残高不足の場合に実行されるハンドラーを指定する
     * @param handler 呼び出し対象
     * @return this
     */
    public @NotNull WithdrawMoneyAction whenInsufficientBalance(@NotNull Consumer<InsufficientBalanceContext> handler) {
        whenInsufficientBalanceHandler = handler;
        return this;
    }

    /**
     * ロールバック時ににユーザーの口座が見つからなかった場合に実行されるハンドラーを指定する
     * @param handler 呼び出し対象
     * @return this
     */
    public @NotNull WithdrawMoneyAction whenRollbackMissingAccount(@NotNull Consumer<MissingAccountContext> handler) {
        whenRollbackMissingAccountHandler = handler;
        return this;
    }

    /**
     * ロールバック時に残高が超過した場合に実行されるハンドラーを指定する
     * @param handler 呼び出し対象
     * @return this
     */
    public @NotNull WithdrawMoneyAction whenRollbackExceededBalance(@NotNull Consumer<ExceededBalanceContext> handler) {
        whenRollbackExceededBalanceHandler = handler;
        return this;
    }

    @Override
    public @NotNull RollbackFunc execute() throws InterruptTransactionException {
        try {
            api.reduceMoney(player, price, reason);
        } catch (UserNotFoundException | MoneyNotFoundException e) {
            whenMissingAccountHandler.accept(new MissingAccountContext());
            throw new InterruptTransactionException("Account not found", e);
        } catch (CanNotReduceMoneyException e) {
            whenInsufficientBalanceHandler.accept(new InsufficientBalanceContext());
            throw new InterruptTransactionException("Insufficient balance", e);
        } catch (ParameterException e) {
            // 復旧不可能であるが念の為返す。
            throw new InterruptTransactionException("must not occur");
        }

        return () -> {
            try {
                api.addMoney(player, price, "[組戻し]" + reason);
            } catch (UserNotFoundException | MoneyNotFoundException e) {
                whenRollbackMissingAccountHandler.accept(new MissingAccountContext());
                throw e;
            } catch (CanNotAddMoneyException e) {
                whenRollbackExceededBalanceHandler.accept(new ExceededBalanceContext());
                throw e;
            }
        };
    }
}
