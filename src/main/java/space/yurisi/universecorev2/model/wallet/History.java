package space.yurisi.universecorev2.model.wallet;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.model.user.UserId;

import java.util.Date;

public record History(
        @NotNull UserId userId,
        @NotNull Long delta,
        @NotNull String reason,
        @NotNull Date timestamp
) {
}
