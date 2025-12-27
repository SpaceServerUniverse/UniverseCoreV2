package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.InterruptTransactionException;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * 指定したユーザーの口座にお金を振り込むアクション
 */
public class DepositMoneyAction implements AtomicRollbackableAction {
    private @NotNull UserRepository userRepository;
    private @NotNull MoneyRepository moneyRepository;
    private @NotNull UUID uuid;
    private @NotNull Long price;
    private @NotNull String reason;

    public record MissingAccountContext(){}
    private @NotNull Consumer<MissingAccountContext> whenMissingAccountHandler = (ctx) -> {};
    private @NotNull Consumer<MissingAccountContext> whenRollbackMissingAccountHandler = (ctx) -> {};

    /**
     * 振り込みアクションを構築する
     *
     * @param uuid お金を振り込む対象のユーザー
     * @param price 振り込む金額(> 0)
     * @param reason 振込理由
     */
    public DepositMoneyAction(
            @NotNull UserRepository userRepository,
            @NotNull MoneyRepository moneyRepository,
            @NotNull UUID uuid,
            @NotNull Long price,
            @NotNull String reason
    ) {
        this.userRepository = userRepository;
        this.moneyRepository = moneyRepository;
        this.uuid = uuid;
        this.price = price;
        this.reason = reason;
    }

    /**
     * 適用時にユーザーの口座が見つからなかった場合に実行されるハンドラーを指定する
     *
     * @param handler 呼び出し対象
     * @return this
     */
    public @NotNull DepositMoneyAction whenMissingAccount(@NotNull Consumer<MissingAccountContext> handler) {
        whenMissingAccountHandler = handler;
        return this;
    }

    /**
     * ロールバック時にユーザーの口座が見つからなかった場合に実行されるハンドラーを指定する
     *
     * @param handler 呼び出し対象
     * @return this
     */
    public @NotNull DepositMoneyAction whenRollbackMissingAccount(@NotNull Consumer<MissingAccountContext> handler) {
        whenRollbackMissingAccountHandler = handler;
        return this;
    }

    @Override
    public @NotNull RollbackFunc execute() throws InterruptTransactionException {
        try {
            User owner = userRepository.getUserFromUUID(uuid);
            Money ownerMoney = moneyRepository.getMoneyFromUserId(owner.getId());
            ownerMoney.setMoney(ownerMoney.getMoney() + price);
            moneyRepository.updateMoney(ownerMoney, price, reason);
        } catch (UserNotFoundException|MoneyNotFoundException e) {
            whenMissingAccountHandler.accept(new MissingAccountContext());
            throw new InterruptTransactionException("Account not found", e);
        }

        return () -> {
            try {
                User owner = userRepository.getUserFromUUID(uuid);
                Money ownerMoney = moneyRepository.getMoneyFromUserId(owner.getId());
                ownerMoney.setMoney(ownerMoney.getMoney() - price);
                moneyRepository.updateMoney(ownerMoney, -price, "[組戻し]" + reason);
            } catch (UserNotFoundException|MoneyNotFoundException e) {
                whenRollbackMissingAccountHandler.accept(new MissingAccountContext());
                throw e;
            }
        };
    }
}
