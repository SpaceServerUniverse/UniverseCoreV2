package space.yurisi.universecorev2.model.user;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.model.wallet.WalletId;

public record User(
        @NotNull UserId id,
        @NotNull WalletId walletId
) {
}
