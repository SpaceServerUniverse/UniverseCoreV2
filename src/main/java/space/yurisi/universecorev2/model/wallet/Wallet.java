package space.yurisi.universecorev2.model.wallet;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.model.user.UserId;

import java.util.UUID;

/**
 * ユーザーの所持金を表すレコード
 * @param id
 * @param balance
 */
public record Wallet(
        @NotNull WalletId id,
        @NotNull Balance balance
) {
    public WalletResult withdraw(TransactionDelta delta) {
        return switch (balance.subtract(delta)) {
            case BalanceResult.Ok(Balance newBalance) -> new WalletResult.Ok(new Wallet(id, newBalance));
            case BalanceResult.Err() -> new WalletResult.Err("残高不足");
        };
    }

    public WalletResult deposit(TransactionDelta delta) {
        return switch (balance.add(delta)) {
            case BalanceResult.Ok(Balance newBalance) -> new WalletResult.Ok(new Wallet(id, newBalance));
            case BalanceResult.Err() -> new WalletResult.Err("上限超過");
        };
    }
}
