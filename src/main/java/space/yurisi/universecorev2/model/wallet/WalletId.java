package space.yurisi.universecorev2.model.wallet;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record WalletId(
        @NotNull UUID value
) {
}
