package space.yurisi.universecorev2.model.wallet;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

public record History(
        @NotNull UUID userId,
        @NotNull Long delta,
        @NotNull String reason,
        @NotNull Date createdAt
) {
}
