package space.yurisi.universecorev2.repository;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.model.user.UserId;
import space.yurisi.universecorev2.model.wallet.Wallet;

import java.util.Optional;

public interface WalletRepository {
    void create(@NotNull Wallet user);
    Optional<Wallet> find(@NotNull UserId id);
    void update(@NotNull Wallet wallet);
    void delete(@NotNull UserId id);
}
