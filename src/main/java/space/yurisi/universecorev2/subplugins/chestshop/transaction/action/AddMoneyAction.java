package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.TransactionException;
import space.yurisi.universecorev2.subplugins.chestshop.utils.ItemUtils;

import java.util.UUID;

public class AddMoneyAction implements AtomicRollbackableAction {
    private @NotNull UserRepository userRepository;
    private @NotNull MoneyRepository moneyRepository;
    private @NotNull UUID uuid;
    private @NotNull Long price;
    private @NotNull String reason;

    public AddMoneyAction(
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

    @Override
    public @NotNull RollbackFunc execute() throws TransactionException {
        try {
            User owner = userRepository.getUserFromUUID(uuid);
            Money ownerMoney = moneyRepository.getMoneyFromUserId(owner.getId());
            ownerMoney.setMoney(ownerMoney.getMoney() + price);
            moneyRepository.updateMoney(ownerMoney, price, reason);
        } catch (UserNotFoundException | MoneyNotFoundException e) {
            throw new TransactionException(
                    "システムエラーです",
                    "user or money not found"
            );
        }

        return () -> {
            try {
                User owner = userRepository.getUserFromUUID(uuid);
                Money ownerMoney = moneyRepository.getMoneyFromUserId(owner.getId());
                ownerMoney.setMoney(ownerMoney.getMoney() - price);
                moneyRepository.updateMoney(ownerMoney, -price, "[組戻し]" + reason);
            } catch (UserNotFoundException | MoneyNotFoundException e) {
                throw new TransactionException("組戻しに失敗しました。管理者に連絡してください", "failed to rollback reducing money action", e);
            }
        };
    }
}
