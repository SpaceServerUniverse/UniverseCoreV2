package space.yurisi.universecorev2.model.wallet;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public record Wallet(
        @NotNull UUID id,
        @NotNull Balance balance
) {
    public Optional<Wallet> withdraw(TransactionDelta delta) {
        return balance.subtract(delta).map(newBalance -> new Wallet(id, newBalance));
    }

    public Optional<Wallet> deposit(TransactionDelta delta) {
        return balance.add(delta).map(newBalance -> new Wallet(id, newBalance));
    }
}
