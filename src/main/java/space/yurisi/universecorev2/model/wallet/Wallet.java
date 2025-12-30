package space.yurisi.universecorev2.model.wallet;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

public record Wallet(
        @NotNull UUID id,
        @NotNull Amount balance,
        @NotNull Date createdAt,
        @NotNull Date updatedAt
) {
}
